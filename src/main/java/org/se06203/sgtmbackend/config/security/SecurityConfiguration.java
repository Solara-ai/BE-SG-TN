package org.se06203.sgtmbackend.config.security;

import lombok.RequiredArgsConstructor;
import org.se06203.sgtmbackend.ultis.Constants;
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
import java.util.Objects;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private static final List<Request> WHITE_LIST = List.of(
            new Request(HttpMethod.OPTIONS, "/**"),
            new Request(null, "/api/*/authentication/*"),
            new Request(null, "/api/*/authentication"),
            new Request(HttpMethod.GET, "/api/public/*"),
            new Request(HttpMethod.GET, "/api/public"),
            new Request(HttpMethod.GET, "/swagger-ui.html"),
            new Request(HttpMethod.GET, "/swagger-ui/*"),
            new Request(null, "/v3/api-docs"),
            new Request(null, "/v3/api-docs.yaml"),
            new Request(null, "/v3/api-docs/*"),
            new Request(HttpMethod.POST, "/api/*/otp/**")
    );

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authz -> {
                            WHITE_LIST.forEach(request -> {
                                if (Objects.isNull(request.method())) {
                                    authz.requestMatchers(request.pattern).permitAll();
                                } else {
                                    authz.requestMatchers(request.method, request.pattern).permitAll();
                                }
                            });
                            authz.requestMatchers("/api/users/**").hasAnyAuthority(Constants.role.USER.name());
                            authz.anyRequest().authenticated();
                        }
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // need this bc spring security 6 by default not retain Context between filters
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
