package org.se06203.besgtn.dto.noti;

import com.google.firebase.messaging.Notification;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageRequest {
    private String recipientToken;
    private String title;
    private String body;
    private String image;
    private Map<String, String> data;


}
