package com.example.cinemahub2.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OtpStorageServiceTest {
    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private OtpStorageService otpStorageService;

    private static final String OTP_PREFIX = "otp:";

    @Test
    void testSetOtp_SetsOtpWithCorrectKeyAndExpiration() {
        // Arrange
        String key = "user@example.com";
        String otp = "123456";
        String prefixedKey = OTP_PREFIX + key;

        // Mock ValueOperations
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // Act
        otpStorageService.setOtp(key, otp);

        // Assert
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).set(prefixedKey, otp, Duration.ofMinutes(5));
    }

    @Test
    void testSetOtp_NullKey_DoesNotThrowException() {
        // Arrange
        String key = null;
        String otp = "123456";
        String prefixedKey = OTP_PREFIX + key;

        // Mock ValueOperations
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // Act
        otpStorageService.setOtp(key, otp);

        // Assert
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).set(prefixedKey, otp, Duration.ofMinutes(5));
    }

    @Test
    void testGetOtp_OtpExists_ReturnsOtp() {
        // Arrange
        String key = "user@example.com";
        String otp = "123456";
        String prefixedKey = OTP_PREFIX + key;

        // Mock ValueOperations
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(prefixedKey)).thenReturn(otp);

        // Act
        String result = otpStorageService.getOtp(key);

        // Assert
        assertEquals(otp, result);
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).get(prefixedKey);
    }

    @Test
    void testGetOtp_OtpDoesNotExist_ReturnsNull() {
        // Arrange
        String key = "user@example.com";
        String prefixedKey = OTP_PREFIX + key;

        // Mock ValueOperations
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(prefixedKey)).thenReturn(null);

        // Act
        String result = otpStorageService.getOtp(key);

        // Assert
        assertNull(result);
        verify(redisTemplate, times(1)).opsForValue();
        verify(valueOperations, times(1)).get(prefixedKey);
    }

    @Test
    void testDeleteOtp_DeletesOtpWithCorrectKey() {
        // Arrange
        String key = "user@example.com";
        String prefixedKey = OTP_PREFIX + key;

        // Act
        otpStorageService.deleteOtp(key);

        // Assert
        verify(redisTemplate, times(1)).delete(prefixedKey);
    }

    @Test
    void testDeleteOtp_NullKey_DoesNotThrowException() {
        // Arrange
        String key = null;
        String prefixedKey = OTP_PREFIX + key;

        // Act
        otpStorageService.deleteOtp(key);

        // Assert
        verify(redisTemplate, times(1)).delete(prefixedKey);
    }
}