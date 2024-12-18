package com.enigma.library_management.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.library_management.constant.Constant;
import com.enigma.library_management.entity.User;
import com.enigma.library_management.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
public class JwtServiceImpl implements JwtService {
    private final String JWT_SECRET;
    private final Long JWT_EXPIRATION;
    private final String ISSUER;
    private final Algorithm algorithm;

    public JwtServiceImpl (
        @Value("${library_management.jwt.secret_key}") String jwtSecret,
        @Value("${library_management.jwt.expiration}") Long jwtExpiration,
        @Value("${library_management.jwt.issuer}") String issuer) {
        JWT_SECRET = jwtSecret;
        JWT_EXPIRATION = jwtExpiration;
        ISSUER = issuer;
        algorithm = Algorithm.HMAC512(jwtSecret);
    };

    @Override
    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withSubject(String.valueOf(user.getId()))
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(JWT_EXPIRATION))
                    .withIssuer(ISSUER)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constant.ERROR_CREATE_TOKEN);
        }
    }

    @Override
    public boolean verifyToken(String bearerToken) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            String JwtToken = extractJwtFromBearerToken(bearerToken);
            verifier.verify(JwtToken);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

    private String extractJwtFromBearerToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return "";
    }

    @Override
    public String getUserIdByToken(String bearerToken) {
        try {
            JWTVerifier jwtVerifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            String JwtToken = extractJwtFromBearerToken(bearerToken);

            DecodedJWT decodedJWT = jwtVerifier.verify(JwtToken);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
