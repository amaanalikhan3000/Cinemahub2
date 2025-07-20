package com.example.cinemahub2.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;


@Service
public class OtpStorageService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String OTP_PREFIX = "otp:";

    public void setOtp(String key, String otp) {
        redisTemplate.opsForValue().set(OTP_PREFIX + key, otp, Duration.ofMinutes(5));
    }

    public String getOtp(String key) {
        Object otp = redisTemplate.opsForValue().get(OTP_PREFIX + key);
        return otp != null ? otp.toString() : null;
    }

    public void deleteOtp(String key) {
        redisTemplate.delete(OTP_PREFIX + key);
    }
}
