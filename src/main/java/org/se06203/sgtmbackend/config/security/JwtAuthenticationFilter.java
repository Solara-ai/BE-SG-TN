package org.se06203.sgtmbackend.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.se06203.sgtmbackend.persistence.repository.UserRolesRepository;
import org.se06203.sgtmbackend.persistence.repository.UsersRepository;
import org.se06203.sgtmbackend.ultis.Constants;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final   UsersRepository userRepository;


    private final String USER_MDC_KEY = "user.id";
    private final String REQUEST_ID = "request.id";
    private final String ENDPOINT = "endpoint";
    private final String METHOD = "http.method";
    private final String TIME_ZONE = "Time-Zone";

    private final String USER_AUTHORITY_MDC_KEY = "user.authority";

    private final List<String> PATH_NOT_FILTER = Arrays.asList(
            "/api/users/authentication",
            "/api/public",
            "/swagger-ui.html",
            "/swagger-ui/",
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/api/users/otp/"
    );
    private final UserRolesRepository userRolesRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        MDC.put(ENDPOINT, request.getRequestURI());
        MDC.put(METHOD, request.getMethod());
        if (StringUtils.isEmpty(bearerToken) || !bearerToken.startsWith("Bearer ")) {
            MDC.put(USER_MDC_KEY, "Anonymous user");
            filterChain.doFilter(request, response);
            return;
        }
        bearerToken = bearerToken.substring(7);
        if (StringUtils.isNotEmpty(bearerToken) && this.jwtService.isTokenValid(bearerToken)) {
            var email = jwtService.getEmailFromToken(bearerToken);
            var role = jwtService.extractUserType(bearerToken);
            userRepository.findByEmailAndRole(email, role)
                    .ifPresent(user -> {
                        var zoneIdString = request.getHeader(TIME_ZONE);
                        var zoneId = StringUtils.isBlank(zoneIdString) ? ZoneId.systemDefault() : ZoneId.of(zoneIdString);
                        var userAuthorities = userRolesRepository.findAllByUserId(Constants.role.USER,user.getId());
                        var sUser = SpringSecurityUser.fromUser(user, zoneId, userAuthorities.stream()
                                .map(authority -> authority.getRole().toString())
                                .toList(), Constants.role.USER);
                        var authentication = new UsernamePasswordAuthenticationToken(
                                sUser, null, sUser.getAuthorities()
                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        MDC.put(USER_MDC_KEY, SecurityUtils.getAuthenticatedUser().getId().toString());
                        MDC.put(USER_AUTHORITY_MDC_KEY, String.join("|", SecurityUtils.getAuthenticatedUser().getAuthorities().stream()
                                .map(Object::toString)
                                .toList()));
                    });
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return PATH_NOT_FILTER.stream().anyMatch(path -> request.getServletPath().startsWith(path));
    }
}
