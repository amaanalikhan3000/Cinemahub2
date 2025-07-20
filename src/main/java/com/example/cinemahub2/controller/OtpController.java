package com.example.cinemahub2.controller;


import com.example.cinemahub2.DTO.OtpRequest;
import com.example.cinemahub2.services.EmailOtpService;
import com.example.cinemahub2.services.OtpStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Random;

@RestController
@RequestMapping("/api/otp")
public class OtpController {


    @Autowired
    private OtpStorageService redisService;

    @Autowired
    private EmailOtpService emailOtpService;


    @PostMapping("/sendOtp")
    public String sendOtp(@RequestBody OtpRequest request) {
        if (request.getEmail() != null) {
            String email = request.getEmail();
            String otp = String.valueOf(new Random().nextInt(8999) + 1000);

            redisService.setOtp(email, otp);

            emailOtpService.sendOtp(email,otp);
            return "OTP sent to email: " + email;
        } else if (request.getPhone() != null) {
            String phone = request.getPhone();
            String otp = String.valueOf(new Random().nextInt(8999) + 1000);
            System.out.println(otp);
            redisService.setOtp(phone, otp);
            return "OTP sent to phone: " + phone;
        } else {
            return "Email or phone number must be provided";
        }
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestBody OtpRequest request) {
        String otp = request.getOtp();

        if (request.getEmail() != null) {
            String email = request.getEmail();
            String savedOtp = redisService.getOtp(email);
            if (otp.equals(savedOtp)) {
                redisService.deleteOtp(email);
                return "Email OTP verified!";
            }
        } else if (request.getPhone() != null) {
            String phone = request.getPhone();
            String savedOtp = redisService.getOtp(phone);
            if (otp.equals(savedOtp)) {
                redisService.deleteOtp(phone);
                return "Phone OTP verified!";
            }
        }
        return "Invalid OTP.";
    }

}