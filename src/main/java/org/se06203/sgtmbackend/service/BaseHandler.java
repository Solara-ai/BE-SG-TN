package org.se06203.sgtmbackend.service;



import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.se06203.sgtmbackend.config.exception.NotFoundException;
import org.se06203.sgtmbackend.config.exception.SocialUserNotFoundException;
import org.se06203.sgtmbackend.config.security.JwtService;
import org.se06203.sgtmbackend.config.security.SecurityUtils;
import org.se06203.sgtmbackend.config.security.SpringSecurityUser;
import org.se06203.sgtmbackend.dto.common.TokenPayload;
import org.se06203.sgtmbackend.dto.response.AuthenticateResponse;
import org.se06203.sgtmbackend.persistence.repository.UsersRepository;
import org.se06203.sgtmbackend.ultis.Constants;
import org.se06203.sgtmbackend.ultis.MapperUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseHandler {

    protected final JwtService jwtService;
    protected final UsersRepository userRepository;
    protected final OTPService otpService;
    protected final PasswordEncoder passwordEncoder;
    protected final UserRolesRepository userRolesRepository;

    protected AuthenticateResponse setAuthenticationContextAndGenerateToken(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = jwtService.createToken(authentication);
        return AuthenticateResponse.builder()
                .token(token.token())
                .refreshToken(jwtService.createRefreshToken(authentication, SecurityUtils.getAuthenticatedUser().getRole()))
                .build();
    }

    public SpringSecurityUser getAuthenticatedUser(String email, String password) {
        return getAuthenticatedUser(email, null, password);
    }

    public SpringSecurityUser getAuthenticatedUser(String email, TokenPayload payload, String password) {
        if (StringUtils.isNotBlank(password)) {
            var user = userRepository
                    .findByEmailAndRole(email, Constants.role.USER)
                    .orElseThrow(() -> new NotFoundException("user", email));

            var userAuthorities = userRolesRepository.findAllByUserId(Constants.role.USER,user.getId());

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new NotFoundException();
            }
            return SpringSecurityUser.fromUser(user, userAuthorities.stream()
                    .map(authority -> authority.getRole().name())
                    .toList(), Constants.role.USER);
        }


        return userRepository
                .findByEmailAndRole(email, Constants.role.USER)
                .map(user -> {
                    var userAuthorities = userRolesRepository.findAllByUserId(Constants.role.USER,user.getId());
                    return SpringSecurityUser.fromUser(user, userAuthorities.stream()
                            .map(authority -> authority.getRole().name())
                            .toList(), Constants.role.USER);
                })
                .orElseThrow(() -> new SocialUserNotFoundException(MapperUtils.toJsonString(payload)));
    }
}
