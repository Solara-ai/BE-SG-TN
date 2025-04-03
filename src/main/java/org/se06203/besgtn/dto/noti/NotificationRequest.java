package org.se06203.besgtn.dto.noti;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationRequest {

    private String title;
    private String message;
    private String tokenFCM;
    private String userId;
    private String status;


}
