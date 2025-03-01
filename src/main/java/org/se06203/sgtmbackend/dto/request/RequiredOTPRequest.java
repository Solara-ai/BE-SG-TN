package org.se06203.sgtmbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequiredOTPRequest {
    @NotNull
    private String transId;

    private String otp;
}
