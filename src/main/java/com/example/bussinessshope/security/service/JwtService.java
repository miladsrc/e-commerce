package com.example.bussinessshope.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${token.validity}")
    private long TOKEN_VALIDITY; // 10 hours
    @Value("${gmc.tag.length}")
    private int GCM_TAG_LENGTH;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String jwt = Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
        return encrypt(jwt);
    }


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        String decryptedToken = decrypt(token);
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(decryptedToken)
                .getPayload();
    }


    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("id", Long.class));
    }

    private SecretKey getAESKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException("Invalid AES key size. Must be 16, 24, or 32 bytes.");
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

    private String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKey key = getAESKey();
            byte[] iv = new byte[12];
            new SecureRandom().nextBytes(iv);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            byte[] combined = new byte[iv.length + encryptedData.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);
            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting data", e);
        }
    }

    private String decrypt(String encryptedData) {
        try {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKey key = getAESKey();
            byte[] iv = Arrays.copyOfRange(decodedData, 0, 12);
            byte[] encryptedBytes = Arrays.copyOfRange(decodedData, 12, decodedData.length);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
            byte[] decryptedData = cipher.doFinal(encryptedBytes);
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting data", e);
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}