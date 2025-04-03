package org.se06203.besgtn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class AddEventReq {
    @Builder.Default
    private String dateTime = Instant.now().toString();
    private String message;
}
