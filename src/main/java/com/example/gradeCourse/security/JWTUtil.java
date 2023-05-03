package com.example.gradeCourse.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationInMinutes}")
    private Integer expirationInMinutes;

    public String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        final long currentTimeMillis = System.currentTimeMillis();
        final long expiration = currentTimeMillis + 1000 * 60 * this.expirationInMinutes;
        String jws = Jwts.builder()
                .setIssuer("SpringSecurityDemo")
                .setSubject(username)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(expiration))
                .signWith(key)
                .compact();
        return jws;
    }

    public String validateTokenAndRetrieveUsername(String jwt) throws JwtException {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> jws;

        jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt);
        return jws.getBody().getSubject();
    }
}