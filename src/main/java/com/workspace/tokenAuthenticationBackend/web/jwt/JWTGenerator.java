package com.workspace.tokenAuthenticationBackend.web.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTGenerator {

    public String accessToken(String email) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("fresh") // (2)
                .setIssuedAt(now) // (3)
                .setExpiration(new Date(now.getTime() + JWTConstant.accessDuration)) // (4)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, "secret") // (6)
                .compact();
    }

    public String refreshToken(String email) {
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("fresh") // (2)
                .setIssuedAt(now) // (3)
                .setExpiration(new Date(now.getTime() + JWTConstant.refreshDuration)) // (4)
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, "secret") // (6)
                .compact();
    }
}
