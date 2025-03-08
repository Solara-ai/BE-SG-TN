package org.se06203.besgtn.config.security;

import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.AuthenticationEntryPoint;
import org.se06203.besgtn.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final List<Request> WHITE_LIST = List.of(
            new Request(HttpMethod.OPTIONS, "/**"),
            new Request(null, "/users/auth/**"),
            new Request(null, "/admin/auth/**"),
            new Request(HttpMethod.GET, "/public/**"),
            new Request(HttpMethod.GET, "/swagger-ui.html"),
            new Request(HttpMethod.GET, "/swagger-ui/**"),
            new Request(HttpMethod.GET, "/v3/api-docs/**"),
            new Request(HttpMethod.GET, "/api-docs/**"),
            new Request(HttpMethod.POST, "/otp/**")
    );

    private static final List<Request> USER = List.of(
            new Request(null, "/users/**")
    );

    private static final List<Request> ADMIN = List.of(
            new Request(null, "/admin/**")
    );

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> {
                    WHITE_LIST.forEach(request -> {
                        if (request.method == null) {
                            authz.requestMatchers(request.pattern).permitAll();
                        } else {
                            authz.requestMatchers(request.method, request.pattern).permitAll();
                        }
                    });

                    USER.forEach(request -> {
                        authz.requestMatchers(request.pattern).hasAuthority(Constants.AuthorityEnum.USER.name());
                    });

                    ADMIN.forEach(request -> {
                        authz.requestMatchers(request.pattern).hasAuthority(Constants.AuthorityEnum.ADMIN.name());
                    });

                    authz.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .securityContext(securityContextConfigurer -> securityContextConfigurer
                        .securityContextRepository(new DelegatingSecurityContextRepository(
                                new RequestAttributeSecurityContextRepository()
                        )))
                .addFilterAt(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new AuthenticationEntryPoint()));

        return http.build();
    }

    public record Request(HttpMethod method, String pattern) {
    }
}
