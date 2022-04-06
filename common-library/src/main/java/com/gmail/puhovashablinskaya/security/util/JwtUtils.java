package com.gmail.puhovashablinskaya.security.util;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {
    @Autowired
    private SecurityUtilConfig securityConfig;

    public Long getUserIdFromJwtToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(securityConfig.getJwtSecret()).parseClaimsJws(token).getBody();
        return claims.get(securityConfig.getUserId(), Long.class);
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(securityConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpiredDateFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(securityConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public Boolean isValidCheckUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(securityConfig.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get(securityConfig.getCheckUserId(), Boolean.class);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(securityConfig.getJwtSecret())
                    .parseClaimsJws(authToken);
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
