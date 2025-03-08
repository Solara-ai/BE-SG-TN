package org.se06203.besgtn.config;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Collections;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
@EnableConfigurationProperties(ApplicationConfigurationProperties.class)
@RequiredArgsConstructor
@Slf4j
public class WebConfigurer implements ServletContextInitializer {

    private final Environment env;
    private final ApplicationConfigurationProperties applicationConfig;

    @Override
    public void onStartup(ServletContext servletContext) {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }
        log.info("Web application fully configured");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        var config = applicationConfig.getCors();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
            log.debug("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
        }
        return new CorsFilter(source);
    }

    @Bean
    public TimeBasedOneTimePasswordGenerator timeBasedOneTimePasswordGenerator() {
        return new TimeBasedOneTimePasswordGenerator(
                Duration.ofSeconds(applicationConfig.getOtp().getTimeStepInSec()),
                applicationConfig.getOtp().getLength(),
                SecurityUtils.OTP_ALGORITHM
        );
    }

    @Bean
    @ConditionalOnBean(TimeBasedOneTimePasswordGenerator.class)
    public KeyGenerator otpKeyGenerator(TimeBasedOneTimePasswordGenerator otpGenerator) throws NoSuchAlgorithmException {
        var keyGenerator = KeyGenerator.getInstance(otpGenerator.getAlgorithm());
        final int macLengthInBytes = Mac.getInstance(otpGenerator.getAlgorithm()).getMacLength();
        keyGenerator.init(macLengthInBytes * 8);
        return keyGenerator;
    }

    @Bean
    public RestClient restClient() {
        var converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        return RestClient.builder()
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(converter))
                .build();
    }
}
