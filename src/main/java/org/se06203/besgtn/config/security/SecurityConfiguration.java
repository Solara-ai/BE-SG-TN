package org.se06203.besgtn.config.security;

import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.utils.Constants;
import org.se06203.besgtn.utils.log.HttpRequestLogger;
import org.se06203.besgtn.utils.RequestContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

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

    @Bean(name = "baseContext")
    @RequestScope
    public RequestContext baseContext() {
        return new RequestContext();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthenticationFilter,
                                           HttpRequestLogger httpRequestLogger) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOriginPatterns(List.of("*"));
                        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                        config.setAllowCredentials(true);
                        config.setMaxAge(3600L);
                        return config;
                    };

                    cors.configurationSource(source);
                })
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(httpRequestLogger, JwtAuthenticationFilter.class);

        return http.build();
    }

    public record Request(HttpMethod method, String pattern) {
    }
}
