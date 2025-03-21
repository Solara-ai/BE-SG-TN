package org.se06203.besgtn.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application")
@Getter
@Setter
public class ApplicationConfigurationProperties {

    private final Security security = new Security();

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Security {

        private final Authentication authentication = new Authentication();

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            @Getter
            @Setter
            public static class Jwt {
                private String secret;
                private String base64Secret;
                private long tokenValidityInSeconds;
                private long tokenValidityInSecondsForRememberMe;
                private long refreshTokenValidityInSeconds;

                public Jwt() {
                    this.tokenValidityInSeconds = 1800L;
                    this.tokenValidityInSecondsForRememberMe = 2592000L;
                    this.refreshTokenValidityInSeconds = 2592000L;
                }
            }
        }
    }
}
