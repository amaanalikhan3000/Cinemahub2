package com.example.cinemahub2.DTO;

import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String phone;
    private String otp;
}

