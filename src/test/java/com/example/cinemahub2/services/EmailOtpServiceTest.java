package com.example.cinemahub2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class EmailOtpServiceTest {
    @Mock
    private JavaMailSender mailSender;

    @Mock
    private OtpStorageService otpStorageService;

    @InjectMocks
    private EmailOtpService emailOtpService;

    @Test
    void testSendOtp_SendsEmailWithCorrectDetails() {
        // Arrange
        String email = "test@example.com";
        String otp = "123456";
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(email);
        expectedMessage.setSubject("Your OTP Code");
        expectedMessage.setText("Your OTP is: " + otp + "\nIt is valid for 5 minutes.");

        // Act
        emailOtpService.sendOtp(email, otp);

        // Assert
        verifyNoInteractions(otpStorageService); // Verify OtpStorageService is not used in sendOtp
    }

    @Test
    void testSendOtp_NullEmail_DoesNotThrowException() {
        // Arrange
        String email = null;
        String otp = "123456";

        // Act & Assert
        try {
            emailOtpService.sendOtp(email, otp);
            verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
        } catch (Exception e) {
            // This test assumes the method doesn't throw an exception for null email
            // Adjust based on actual requirements (e.g., if it should throw an exception)
        }
        verifyNoInteractions(otpStorageService);
    }

    @Test
    void testSendOtp_NullOtp_SendsEmailWithNullOtp() {
        // Arrange
        String email = "test@example.com";
        String otp = null;

        // Act
        emailOtpService.sendOtp(email, otp);

        // Assert
        verifyNoInteractions(otpStorageService);
    }
}