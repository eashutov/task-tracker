package ru.shutov.itone.tasktracker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.shutov.itone.tasktracker.configuration.JwtProperties;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static final String ISSUER = "ru.shutov.itone.tasktracker";
    private static final String SUBJECT = "user_details";
    private final JwtProperties jwtProperties;

    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(7).toInstant());
        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtProperties.getSecret()));
    }

    public DecodedJWT decodeToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecret()))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .build();
        return verifier.verify(token);
    }

    public String retrieveUsername(String token) throws JWTVerificationException {
        return decodeToken(token).getClaim("username").asString();
    }
}
