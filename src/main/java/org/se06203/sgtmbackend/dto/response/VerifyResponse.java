package org.se06203.sgtmbackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifyResponse {
    private String transId;
}
