package org.se06203.sgtmbackend.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOTPRequest extends RequiredOTPRequest {
    @NotNull
    private String otp;
}

