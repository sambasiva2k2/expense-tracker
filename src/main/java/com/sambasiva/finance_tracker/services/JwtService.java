package com.sambasiva.finance_tracker.services;

import com.sambasiva.finance_tracker.models.User;
import com.sambasiva.finance_tracker.models.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${spring.security.secretKey}")
    private String secretKey;

    public String generateToken(User user) {
        return Jwts.builder()
                .signWith(getKey())
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .claim("role", user.getRole())
                .claim("userId", user.getUserId())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .compact();
    }

    public Key getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Integer extractUserId(String token) {
        Claims claim = extractAllClaims(token);
        return (Integer) claim.get("userId");
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(UserPrincipal user, String token) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }
}
