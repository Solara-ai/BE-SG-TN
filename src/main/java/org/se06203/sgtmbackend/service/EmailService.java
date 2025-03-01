package org.se06203.sgtmbackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    public final JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Password Reset OTP Code");
        message.setText("Your OTP code is:"  + otp + ". It will expire in 5 minutes.");
        mailSender.send(message);
    }
}
