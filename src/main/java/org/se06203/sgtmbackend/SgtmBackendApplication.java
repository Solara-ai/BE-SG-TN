package org.se06203.sgtmbackend;

import org.se06203.sgtmbackend.config.ApplicationConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ApplicationConfigurationProperties.class)
@SpringBootApplication
public class SgtmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SgtmBackendApplication.class, args);
    }

}
