package com.gmail.puhovashablinskaya.controller.security.util;

import com.gmail.puhovashablinskaya.controller.security.config.JwtUtilConfig;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@AllArgsConstructor
public class JwtUtils {
    private final JwtUtilConfig utilConfig;

    public String generateJwtToken(String username, Long userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim(utilConfig.getUserId(), userId)
                .claim(utilConfig.getCheckUserId(), utilConfig.getIsCheckUser())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + utilConfig.getJwtExpirationMs()))
                .signWith(SignatureAlgorithm.HS512, utilConfig.getJwtSecret())
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(utilConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(utilConfig.getJwtSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claim string is empty: {}", e.getMessage());
        }
        return false;
    }
}
