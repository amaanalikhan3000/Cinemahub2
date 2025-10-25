package com.example.cinemahub2.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

import static java.util.Optional.ofNullable;


@Service
public class OtpStorageService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String OTP_PREFIX = "otp:";

    public void setOtp(String key, String otp) {
        redisTemplate.opsForValue().set(OTP_PREFIX + key, otp, Duration.ofMinutes(5));
    }

    public String getOtp(String key) {
        return ofNullable(redisTemplate.opsForValue().get(OTP_PREFIX + key))
                .map(Object::toString)
                .orElse(null);
    }

    public void deleteOtp(String key) {
        redisTemplate.delete(OTP_PREFIX + key);
    }
}
