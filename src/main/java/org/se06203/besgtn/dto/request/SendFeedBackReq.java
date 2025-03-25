package org.se06203.besgtn.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SendFeedBackReq {
    private String message;
}
