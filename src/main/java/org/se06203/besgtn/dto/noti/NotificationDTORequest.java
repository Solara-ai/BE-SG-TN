package org.se06203.besgtn.dto.noti;

import lombok.*;
import org.se06203.besgtn.persistence.entity.Notifications;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTORequest {
    private List<Notifications> recipient;
    private String message;
}
