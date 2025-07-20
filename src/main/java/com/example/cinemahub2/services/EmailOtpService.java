package com.example.cinemahub2.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailOtpService {

    @Autowired
    private org.springframework.mail.javamail.JavaMailSender mailSender;

    @Autowired
    private OtpStorageService otpStorageService;

    public EmailOtpService(JavaMailSender mailSender, OtpStorageService otpStorageService) {
        this.mailSender = mailSender;
        this.otpStorageService = otpStorageService;
    }


    public void sendOtp(String email,String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\nIt is valid for 5 minutes.");
        mailSender.send(message);
    }


}