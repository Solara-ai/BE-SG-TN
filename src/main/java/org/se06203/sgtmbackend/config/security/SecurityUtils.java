package org.se06203.sgtmbackend.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityUtils {
    public static final String JWT_ALGORITHM = "HmacSHA256";
    public static final String OTP_ALGORITHM = "HmacSHA256";

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String s) {
            return s;
        }
        return null;
    }

    public static Optional<String> getCurrentUserLogin() {
        return Optional.ofNullable(extractPrincipal(getAuthentication()));
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority);
    }

    public static ZoneId getUserTimeZone() {
        return getAuthenticatedUser().getZoneId();
    }

    public static boolean hasRoles(String... authorities) {
        Authentication authentication = getAuthentication();
        return authentication != null &&
                getAuthorities(authentication)
                        .anyMatch(authority -> Arrays.asList(authorities).contains(authority));
    }

    public static SpringSecurityUser getAuthenticatedUser() {
        return (SpringSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                instanceof UsernamePasswordAuthenticationToken;
    }
}
