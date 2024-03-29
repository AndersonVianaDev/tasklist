package com.anderson.tasklist.external.auth;

import com.anderson.tasklist.core.shared.exceptions.InvalidDataException;
import com.anderson.tasklist.core.shared.exceptions.TokenGeneratorException;
import com.anderson.tasklist.core.user.model.User;
import com.anderson.tasklist.core.user.services.TokenGenerator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Component
public class TokenService implements TokenGenerator {

    @Value("${security.token.secret}")
    private String secret;

    @Override
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getId().toString())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new TokenGeneratorException("Error while generating token" + e);
        }
    }

    @Override
    public UUID validateToken(String token) {
        try {
            token = token.replace("Bearer ", "");
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return UUID.fromString(JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject());
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    public void verifyToken(String token, UUID idUser) {
        UUID id = validateToken(token);

        if(!id.equals(idUser)) {
            throw new InvalidDataException("Not authorized");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
