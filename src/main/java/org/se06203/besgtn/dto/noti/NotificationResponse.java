package org.se06203.besgtn.dto.noti;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotificationResponse {
    private String title;
    private String message;
    private Long sendTime;
    private String data;
    private String icon;
}
