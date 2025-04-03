package org.se06203.besgtn.controller.user.noti;


import com.google.firebase.messaging.Notification;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.noti.NotificationMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


@RequestMapping("/notification")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Notification", description = "Api for user authentication")
public interface NotificationApi {
    @PostMapping("/send")
    ResponseEntity<BaseResponse> sendNotification(@RequestBody NotificationMessageRequest request);

}
