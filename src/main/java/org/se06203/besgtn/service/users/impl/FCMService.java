package org.se06203.besgtn.service.users.impl;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.dto.noti.NotificationMessageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMService {
    private final FirebaseMessaging firebaseMessaging;

    public String sendNotification(NotificationMessageRequest request) {

        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .setImage(request.getImage())
                .build();

        Message message = Message.builder()
                .setToken(request.getRecipientToken())
                .setNotification(notification)
                .putAllData(request.getData())
                .build();

        try {
            firebaseMessaging.send(message);

            return "Notification sent successfully";

        }
        catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "Error while sending notification";




        }
    }
}
