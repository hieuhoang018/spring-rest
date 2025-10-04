package com.hieuhoang.springrest.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;
import java.util.Map;


@Service
public class JwtService {


    private final Key key;
    private final long expirationMs;


    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long expirationMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }


    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractSubject(String token) {
        return parse(token).getBody().getSubject();
    }


    public boolean isValid(String token, String subject) {
        try {
            return extractSubject(token).equals(subject) && !isExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }


    private boolean isExpired(String token) {
        return parse(token).getBody().getExpiration().before(new Date());
    }


    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
