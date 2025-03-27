package org.se06203.besgtn.service;

import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.security.JwtService;
import org.se06203.besgtn.config.security.SpringSecurityUser;
import org.se06203.besgtn.dto.request.EmailRequest;
import org.se06203.besgtn.dto.request.RegisterAdminRequest;
import org.se06203.besgtn.dto.request.RegisterUserRequest;
import org.se06203.besgtn.dto.response.AuthenticateResponse;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.persistence.entity.Users;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.se06203.besgtn.utils.Constants;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuthService extends BaseHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(jwtService, userRepository, passwordEncoder);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthenticateResponse authenticate(EmailRequest request, Constants.AuthorityEnum authority) {

        SpringSecurityUser springSecurityUser = getAuthenticatedUser(request.getEmail(),
                request.getPassword(),
                authority);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                springSecurityUser,
                request.getPassword(),
                springSecurityUser.getAuthorities()
        );

        try {
            return super.setAuthenticationContextAndGenerateToken(authenticationToken,
                    springSecurityUser.getId(),
                    request.getRememberMe());
        } catch (BadCredentialsException ex) {
            throw new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String registerUser(RegisterUserRequest request) {
        userRepository.findByEmailAndRoleIn(request.getEmail(), Constants.AuthorityEnum.USER.name())
                .ifPresent(users -> {
                    throw new BaseRuntimeException(ErrorCodeMsg.USER_ALREADY_EXIST);
                });

        List<Constants.AuthorityEnum> roles = new ArrayList<>();
        roles.add(Constants.AuthorityEnum.USER);

        return userRepository.save(Users.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .hobbies(request.getHobbies())
                .fullName(request.getFullName())
                .birthday(request.getBirthday())
                .occupation(request.getOccupation())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build()).getId();
    }

    @Transactional
    public String register(RegisterAdminRequest request) {
        var user = userRepository.findById(request.getId())
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND));
        if (user.getRoles().contains(Constants.AuthorityEnum.ADMIN)) {
            throw new BaseRuntimeException(ErrorCodeMsg.USER_ALREADY_EXIST);
        }

        user.getRoles().add(Constants.AuthorityEnum.ADMIN);
        userRepository.save(user);
        return user.getId();
    }
}
