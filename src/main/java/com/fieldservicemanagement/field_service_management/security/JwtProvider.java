package com.fieldservicemanagement.field_service_management.security;

import com.fieldservicemanagement.field_service_management.config.prop.JwtProp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProp jwtProp;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProp.getSecret().getBytes());
    }

    public String getEmailFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String generateToken(String email, boolean isAccess) {
        long now = System.currentTimeMillis();
        long expire = isAccess ? jwtProp.getAccessTtl().toMillis() : jwtProp.getRefreshTtl().toMillis();
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(now + expire))
                .signWith(key)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            return ex.getClaims();
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException ex) {
            throw new RuntimeException("JWT_PARSE_EXCEPTION ", ex);
        }
    }
}
