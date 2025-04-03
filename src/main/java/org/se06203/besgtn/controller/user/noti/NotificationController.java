package org.se06203.besgtn.controller.user.noti;
import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.noti.NotificationMessageRequest;
import org.se06203.besgtn.service.users.impl.FCMService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi
{

    private final FCMService fcmService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<BaseResponse> sendNotification(NotificationMessageRequest request) {
        return responseFactory.success(HttpStatus.OK,fcmService.sendNotification(request));
    }
}
