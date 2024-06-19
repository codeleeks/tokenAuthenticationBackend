package com.workspace.tokenAuthenticationBackend.web.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JWTTokenProvider {
    public Claims parseJwtToken(String tokenHeader) {
        validationAuthorizationHeader(tokenHeader); // (1)
        log.info("token {}", tokenHeader);
        return Jwts.parser()
                .setSigningKey("secret") // (3)
                .parseClaimsJws(tokenHeader) // (4)
                .getBody();
    }

    public boolean hasTokenExpired(String tokenHeader) {
        try {
            parseJwtToken(tokenHeader);
            return false;
        } catch (ExpiredJwtException | IllegalArgumentException e) {
            log.error("expired! " + e.getMessage());
        }
        return true;
    }

    private void validationAuthorizationHeader(String header) {
        if (header == null || header.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}
