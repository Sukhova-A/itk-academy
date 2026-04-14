package com.anastasiia.itkacademy.service;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JWTUtils {

    // Ключ шифрования для JWT
    private SecretKey secretKey;

    // Время действия токена в миллисекундах (24 часа)
    public static final long REFRESH_EXPIRATION_TIME = 60 * 60 * 24 * 1000;
    public static final long ACCESS_EXPIRATION_TIME = 60 * 10 * 1000;

    public JWTUtils(){
        // Строка, используемая для создания секретного ключа
        String secretString = "MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDE=";
        byte[] keyBytes = Base64.getDecoder().decode(secretString);
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    /*Метод для генерации JWT токена на основе данных пользователя*/
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    // Метод для генерации токена обновления (refresh token) с дополнительными данными
    public String generateRefreshToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Метод для извлечения имени пользователя из токена
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsTFunction.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}