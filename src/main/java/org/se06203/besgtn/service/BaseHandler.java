package org.se06203.besgtn.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.security.JwtService;
import org.se06203.besgtn.config.security.SpringSecurityUser;
import org.se06203.besgtn.dto.response.AuthenticateResponse;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.se06203.besgtn.utils.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        var token = jwtService.createToken(authentication);
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
}
