package com.example.cinemahub2.configAndUtility;

import com.example.cinemahub2.entity.AppUser;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {


        private final String SECRET_KEY = "secret";

        public String generateToken(AppUser username) {
            return Jwts.builder()
                    .setSubject(username.getEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        }

        public String extractUsername(String token) {
            return Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token).getBody().getSubject();
        }

        public boolean validateToken(String token, UserDetails userDetails) {
            return extractUsername(token).equals(userDetails.getUsername()) &&
                    !isTokenExpired(token);
        }

        private boolean isTokenExpired(String token) {
            Date expiration = Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token).getBody().getExpiration();
            return expiration.before(new Date());
        }

}