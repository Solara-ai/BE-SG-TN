package org.se06203.besgtn.service.users.impl.auth;

import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.NotFoundException;
import org.se06203.besgtn.config.security.JwtService;
import org.se06203.besgtn.config.security.SpringSecurityUser;
import org.se06203.besgtn.dto.request.EmailRequest;
import org.se06203.besgtn.dto.request.RegisterRequest;
import org.se06203.besgtn.dto.response.AuthenticateResponse;
import org.se06203.besgtn.exception.ErrorCodeMsg;
import org.se06203.besgtn.persistence.entity.Users;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.se06203.besgtn.service.BaseHandler;
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
    public AuthenticateResponse authenticate(EmailRequest request) {

        SpringSecurityUser springSecurityUser = getAuthenticatedUser(request.getEmail(), request.getPassword());

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                springSecurityUser,
                request.getPassword(),
                springSecurityUser.getAuthorities()
        );

        try {
            return super.setAuthenticationContextAndGenerateToken(authenticationToken);
        } catch (BadCredentialsException ex) {
            throw new NotFoundException("Cannot found account");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String register(RegisterRequest request){
        var user = userRepository.findByEmailAndRoleIn(request.getEmail(), Constants.AuthorityEnum.USER.name());
        var existingUser = new Users();
        if (user.isPresent()) {
            existingUser = user.get();

            if (existingUser.getRoles().contains(Constants.AuthorityEnum.USER.name())) {
                throw new BaseRuntimeException(ErrorCodeMsg.USER_ALREADY_EXIST);
            }

            existingUser.getRoles().add(Constants.AuthorityEnum.USER.name());
        }else {
            List<String> roles = new ArrayList<>();
            roles.add(Constants.AuthorityEnum.USER.name());
            existingUser.setRoles(roles);
        }

        Users newUser = userRepository.save(existingUser.toBuilder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());

        return newUser.getId();
    }
}
