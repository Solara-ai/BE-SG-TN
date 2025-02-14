package org.se06203.sgtmbackend.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties("application")
@Getter
@Setter
public class ApplicationConfigurationProperties {
    /**
     * API version header:
     * Change API_VERSION to the version of the API you are working on
     */
    private static final String API_VERSION = "v1.0";
    private static final String API_VERSION_HEADER_KEY = "X-API-Version";
    public static final String API_VERSION_HEADER = API_VERSION_HEADER_KEY + "=" + API_VERSION;

    private final Security security = new Security();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final ThirdParty thirdParty = new ThirdParty();
    private final OTP otp = new OTP();

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

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ThirdParty {
        private final Google google = new Google();
        private final Apple apple = new Apple();
        private final WhatsApp whatsApp = new WhatsApp();

        @Getter
        @Setter
        public static class Google {
            private String clientId;
            private String clientSecret;
            private String bucketName;
            private String projectId;
            private String publicBucketName;
            private String apiKey;
            private String credentialFilePath;
        }

        @Getter
        @Setter
        public static class Apple {
            private String appAudience;
        }

        @Getter
        @Setter
        public static class WhatsApp {
            private String accessToken;
            private String phoneNumberId;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OTP {
        private Integer timeStepInSec = 30;
        private Integer resultValidityInSec = 300;
        private Integer length = 6;
    }
}
