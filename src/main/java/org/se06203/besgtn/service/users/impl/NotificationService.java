package org.se06203.besgtn.service.users.impl;


import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.dto.noti.NotificationRequest;
import org.se06203.besgtn.dto.noti.NotificationResponse;
import org.se06203.besgtn.persistence.entity.Notifications;
import org.se06203.besgtn.persistence.repository.NotificationRepository;
import org.se06203.besgtn.utils.Constants;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class NotificationService {


    private final NotificationRepository notificationRepository;

    private final FirebaseMessaging firebaseMessaging;






    public void sendNotification(NotificationRequest request) {

        var notifications = Notifications.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .status(Constants.StatusNotification.valueOf(request.getStatus()))
                .token(request.getTokenFCM())
                .build();
        notificationRepository.save(notifications);
    }








//    public NotificationResponse sendNotification(NotificationRequest request){
//        var userNotification = notificationRepository.findByUserIdAndStatus(request.getUserId(),request.getStatus());
//        if (userNotification == null || userNotification.getToken() == null) {
//            throw new RuntimeException("User Firebase Token not found");
//        }
//        String firebaseToken = userNotification.getToken();
//
//        Message firebaseMessage =Message.builder()
//                .setToken(firebaseToken)
//                .setNotification(
//                        com.google.firebase.messaging.Notification.builder()
//                                .setTitle(request.getTitle())
//                                .setBody(request.getMessage())
//                                .build()
//                ).build();
//        try {
//            firebaseMessaging.send(firebaseMessage);
//            notificationRepository.save(userNotification);
//            var notification = Notifications.builder()
//                            .title(request.getTitle())
//                                    .message(request.getMessage())
//                                            .status(Constants.StatusNotification.valueOf(request.getStatus()))
//                                                    .token(request.getTokenFCM())
//                                                            .build();
//        } catch (FirebaseMessagingException e) {
//            throw new RuntimeException("Failed to send notification", e);
//        }
////        var notificationResponse = NotificationResponse.builder()
////                .title(request.getTitle())
////                .message(request.getMessage())
////                .
////
////                .build();
//
//    }



}
