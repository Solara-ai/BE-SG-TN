package org.se06203.besgtn.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.security.JwtService;
import org.se06203.besgtn.config.security.SpringSecurityUser;
import org.se06203.besgtn.dto.request.RefreshTokenReq;
import org.se06203.besgtn.dto.response.AuthenticateResponse;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.se06203.besgtn.utils.Constants;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaseHandler {

    protected final JwtService jwtService;
    protected final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    protected AuthenticateResponse setAuthenticationContextAndGenerateToken(Authentication authentication,
                                                                            String userId,
                                                                            Boolean rememberMe) {

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = jwtService.createToken(authentication, rememberMe);
        return AuthenticateResponse.builder()
                .token(token.token())
                .refreshToken(jwtService.createRefreshToken(authentication))
                .userId(userId)
                .rememberMe(rememberMe)
                .build();
    }

    public SpringSecurityUser getAuthenticated(String email, String password, Constants.AuthorityEnum authority) {
        return switch (authority) {
            case USER -> getAuthenticatedUser(email, password, authority);
            case ADMIN -> getAuthenticatedAdmin(email, password, authority);
            default -> throw new BaseRuntimeException(ErrorCodeMsg.INVALID_AUTHORITY);
        };
    }

    private SpringSecurityUser getAuthenticatedUser(String email,
                                                    String password,
                                                    Constants.AuthorityEnum authority) {
        if (StringUtils.isNotBlank(password)) {
            var user = userRepository
                    .findByEmailAndRoleIn(email, authority.name())
                    .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND));


            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BaseRuntimeException(ErrorCodeMsg.PASSWORD_OR_EMAIL_NOT_MATCH);
            }
            return SpringSecurityUser.fromUser(user, Constants.AuthorityEnum.USER);
        }


        return userRepository
                .findByEmailAndRoleIn(email, Constants.AuthorityEnum.USER.name())
                .map(user -> SpringSecurityUser.fromUser(user, Constants.AuthorityEnum.USER))
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND));
    }

    public SpringSecurityUser getAuthenticatedUser(String email, Constants.AuthorityEnum userType) {
        return getAuthenticatedUser(email, null, userType);
    }

    private SpringSecurityUser getAuthenticatedAdmin(String email,
                                                     String password,
                                                     Constants.AuthorityEnum authority) {
        if (StringUtils.isNotBlank(password)) {
            var user = userRepository
                    .findByEmailAndRoleIn(email, authority.name())
                    .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND));


            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BaseRuntimeException(ErrorCodeMsg.PASSWORD_OR_EMAIL_NOT_MATCH);
            }
            return SpringSecurityUser.fromUser(user, Constants.AuthorityEnum.ADMIN);
        }


        return userRepository
                .findByEmailAndRoleIn(email, Constants.AuthorityEnum.ADMIN.name())
                .map(user -> SpringSecurityUser.fromUser(user, Constants.AuthorityEnum.ADMIN))
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.USER_NOT_FOUND));
    }

    protected AuthenticateResponse refreshToken(RefreshTokenReq req, Constants.AuthorityEnum authorityEnum) {
        try {
            if (jwtService.isTokenValid(req.getRefreshToken())) {
                var expireTime = jwtService.extractExpiration(req.getRefreshToken());
                var userName = jwtService.extractUsername(req.getRefreshToken());
                var authenUser = getAuthenticatedUser(userName, authorityEnum);
                var authentication = new UsernamePasswordAuthenticationToken(authenUser,
                        "",
                        authenUser.getAuthorities());
                if (expireTime.toInstant().isAfter(Instant.now())
                        && expireTime.toInstant().minus(1, ChronoUnit.DAYS).isBefore(Instant.now())) {
                    return AuthenticateResponse.builder()
                            .token(jwtService.createToken(authentication).token())
                            .refreshToken(jwtService.createRefreshToken(authentication))
                            .userId(authenUser.getId())
                            .rememberMe(true)
                            .build();
                } else {
                    return AuthenticateResponse.builder()
                            .token(jwtService.createToken(authentication).token())
                            .refreshToken(req.getRefreshToken())
                            .userId(authenUser.getId())
                            .rememberMe(true)
                            .build();
                }
            }
            throw new BaseRuntimeException(ErrorCodeMsg.INVALID_REFRESH_TOKEN);
        } catch (Exception exception) {
            log.error("Error while refreshing token", exception);
            throw new BaseRuntimeException(ErrorCodeMsg.INVALID_REFRESH_TOKEN);
        }
    }
}
