package org.se06203.sgtmbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.se06203.sgtmbackend.config.ErrorCode;
import org.se06203.sgtmbackend.config.exception.OtpInvalidException;
import org.se06203.sgtmbackend.config.exception.ServerException;
import org.se06203.sgtmbackend.config.security.SecurityUtils;
import org.se06203.sgtmbackend.dto.request.RequiredOTPRequest;
import org.se06203.sgtmbackend.dto.request.VerifyEmailRequest;
import org.se06203.sgtmbackend.dto.request.VerifyOTPRequest;
import org.se06203.sgtmbackend.dto.response.VerifyResponse;
import org.se06203.sgtmbackend.ultis.Constants;
import org.se06203.sgtmbackend.ultis.RandomUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class OTPService {

    private final EmailService emailService;

    // Map để lưu trữ các giao dịch OTP
    private final Map<String, OtpTransaction> otpTransactions = new HashMap<>();

    private OtpTransaction generateOTP(String email) {
        String otpCode = String.format("%04d", new Random().nextInt(9000) + 1000);
        Instant expiryTime = Instant.now().plus(Constants.EXPIRY_DURATION_MINUTES, ChronoUnit.MINUTES);

        return new OtpTransaction(otpCode, expiryTime, email);
    }

    private boolean validateOtp(String transId, String otp) {
        OtpTransaction otpTransaction = otpTransactions.get(transId);
        return otpTransaction != null && otpTransaction.otpCode().equals(otp)
                && Instant.now().isBefore(otpTransaction.expiryTime());
    }

    public RequiredOTPRequest generateOTP(VerifyEmailRequest rq) {
        try {
            String transId = RandomUtils.generateOtpTransId();
            String email;
            if (SecurityUtils.isAuthenticated()) {
                email = SecurityUtils.getAuthenticatedUser().getEmail();
            } else {
                email = Objects.requireNonNull(rq.getEmail());
            }

            OtpTransaction otpTransaction = this.generateOTP(rq.getEmail());
            this.saveOTPTransaction(transId, otpTransaction);
            emailService.sendOtpEmail(email, otpTransaction.otpCode());
            return new RequiredOTPRequest(transId, otpTransaction.otpCode);
        } catch (Exception e) {
            throw new ServerException(ErrorCode.INVALID);
        }
    }

    private void saveOTPTransaction(String transId, OtpTransaction otpTransaction) {
        otpTransactions.put(transId, otpTransaction);
    }

    public VerifyResponse verifyOTP(VerifyOTPRequest rq) {
        if (!this.validateOtp(rq.getTransId(), rq.getOtp())) {
            throw new OtpInvalidException();
        }

        return VerifyResponse.builder()
                .transId(rq.getTransId())
                .build();
    }

    public boolean isValidOtpTransaction(String transId, String email) {
        OtpTransaction otpTransaction = otpTransactions.get(transId);
        otpTransactions.remove(transId);
        return otpTransaction != null && otpTransaction.email.equals(email);
    }

    // Lớp record để lưu trữ thông tin OTP
    record OtpTransaction(String otpCode, Instant expiryTime, String email) {
    }
}
