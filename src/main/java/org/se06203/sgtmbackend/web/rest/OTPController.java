package org.se06203.sgtmbackend.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.se06203.sgtmbackend.config.exception.response.ResponseWrapper;
import org.se06203.sgtmbackend.dto.request.RequiredOTPRequest;
import org.se06203.sgtmbackend.dto.request.VerifyEmailRequest;
import org.se06203.sgtmbackend.dto.request.VerifyOTPRequest;
import org.se06203.sgtmbackend.dto.response.VerifyResponse;
import org.se06203.sgtmbackend.service.OTPService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/otp")
@SecurityRequirement(name = "Bearer Authentication")
public class OTPController {

    private final OTPService otpService;

    @PostMapping("/request")
    @Operation(tags = "User - OTP")
    public ResponseWrapper<RequiredOTPRequest> requestOTP(@Validated @RequestBody VerifyEmailRequest rq) {
        return ResponseWrapper.success(otpService.generateOTP(rq));
    }

    @PostMapping("/verify")
    @Operation(tags = "User - OTP")
    public ResponseWrapper<VerifyResponse> verifyOTP(@RequestBody VerifyOTPRequest rq) {
        return ResponseWrapper.success(otpService.verifyOTP(rq));
    }
}
