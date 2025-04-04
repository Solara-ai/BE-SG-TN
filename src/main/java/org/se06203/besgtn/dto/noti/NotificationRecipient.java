package org.se06203.besgtn.dto.noti;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRecipient {
    private String userDeviceToken;
}
