package org.se06203.besgtn.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.Data;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Data
@Configuration
@ConfigurationProperties("fcm")
public class FCMConfig {

    private String serviceAccountKey;
    @Bean
    FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
    @Bean
    FirebaseApp firebaseApp(GoogleCredentials credentials) {
        if (FirebaseApp.getApps().isEmpty()) {
            val options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();

            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }

    }
    @Bean
    GoogleCredentials googleCredentials() throws IOException {
        if (StringUtils.isNotEmpty(serviceAccountKey)) {
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(serviceAccountKey)) {
                return GoogleCredentials.fromStream(is);
            }
        } else {
            return GoogleCredentials.getApplicationDefault();
        }
    }


//    @Bean
//    FirebaseMessaging firebaseMessaging() throws IOException {
//        GoogleCredentials googleCredentials = GoogleCredentials
//                .fromStream(
//                new ClassPathResource("sgtm-backend-firebase-adminsdk-fbsvc-9c3885b77.json").getInputStream());
//        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
//                .setCredentials(googleCredentials()).build();
//
//        FirebaseApp firebaseApp = FirebaseApp.initializeApp(firebaseOptions,"my-app");
//        return FirebaseMessaging.getInstance(firebaseApp);
//
//    }

}
