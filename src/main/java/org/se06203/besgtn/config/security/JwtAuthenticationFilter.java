package org.se06203.besgtn.config.security;

import java.util.UUID;
import java.util.List;
import lombok.NonNull;
import java.util.Arrays;
import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.se06203.besgtn.persistence.repository.UserRepository;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final String USER_MDC_KEY = "user.id";
    private final String REQUEST_ID = "request.id";
    private final String ENDPOINT = "endpoint";
    private final String METHOD = "http.method";

    private final String USER_AUTHORITY_MDC_KEY = "user.authority";

    private final List<String> PATH_NOT_FILTER = Arrays.asList(
            "/users/auth",
            "/admin/auth",
            "/public",
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/swagger-ui/index.html",
            "/v3/api-docs",
            "/users/otp",
            "/admin/otp"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        var bearerToken = request.getHeader("Authorization");
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
            var userId = jwtService.getUserIdFromToken(bearerToken);
            var role = jwtService.getAuthFromToken(bearerToken);
            userRepository.findByIdAndRole(userId, role)
                    .ifPresent(user -> {
                        var sUser = SpringSecurityUser.fromUser(user, role);
                        var authentication = new UsernamePasswordAuthenticationToken(
                                sUser, null, sUser.getAuthorities()
                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        MDC.put(USER_MDC_KEY, SecurityUtils.getAuthenticatedUser().getId());
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
