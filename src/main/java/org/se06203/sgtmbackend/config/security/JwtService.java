package org.se06203.sgtmbackend.config.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.se06203.sgtmbackend.config.ApplicationConfigurationProperties;
import org.se06203.sgtmbackend.config.ErrorCode;
import org.se06203.sgtmbackend.config.exception.ServerException;
import org.se06203.sgtmbackend.ultis.Constants;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private SecretKey secretKey;

    private final ApplicationConfigurationProperties appProperties; ;

    private static final String CLAIM_AUTHORITY = "authority";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_FIRST_NAME = "firstName";
    private static final String CLAIM_LAST_NAME = "lastName";
    private static final String CLAIM_PHONE = "phone";
    private static final String CLAIM_USERNAME = "userName";

    @PostConstruct
    public void init() {
        String secret = appProperties.getSecurity().getAuthentication().getJwt().getBase64Secret();
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, SecurityUtils.JWT_ALGORITHM);
    }

    private Claims extractAllClaims(String token) {
        return this.defaultJwtParserBuilder()
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @SneakyThrows
    public Constants.role extractAuthority(String token) {
        var refType = new TypeReference<Set<HashMap<String, String>>>() {
        };
        var authorities = new ObjectMapper().convertValue(extractClaim(token, claims -> claims.get(CLAIM_ROLE, Object.class)), refType);
        var authority = authorities.stream()
                .findFirst()
                .orElseThrow(() -> new ServerException(ErrorCode.UNAUTHORIZED));
        return Constants.role.valueOf(authority.get("authority"));
    }

    @SneakyThrows
    public Constants.role extractUserType(String token) {
        var role = extractClaim(token, claims -> claims.get(CLAIM_ROLE, String.class));
        return Constants.role.valueOf(role);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token) {
        try {
            this.defaultJwtParserBuilder().build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

    public String getEmailFromToken(String token) {
        return this.defaultJwtParserBuilder()
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public TokenInfo createToken(Authentication authentication) {
        return this.createToken(authentication, false);
    }

    public String createRefreshToken(Authentication authentication, Constants.role userType) {
        var jwtConfig = appProperties.getSecurity().getAuthentication().getJwt();
        Date validity = new Date(System.currentTimeMillis() +
                (jwtConfig.getRefreshTokenValidityInSeconds() * 1000));

        return this.defaultJwtBuilder()
                .subject(authentication.getName())
                .claim(CLAIM_ROLE, userType)
                .expiration(validity)
                .compact();

    }

    public TokenInfo createToken(Authentication authentication, boolean rememberMe) {
        var jwtConfig = appProperties.getSecurity().getAuthentication().getJwt();
        var authenticateUser = (SpringSecurityUser) authentication.getPrincipal();
        Date validity = new Date(System.currentTimeMillis() +
                (rememberMe
                        ? jwtConfig.getTokenValidityInSecondsForRememberMe()
                        : jwtConfig.getTokenValidityInSeconds()) * 1000);

        return new TokenInfo(this.defaultJwtBuilder()
                .claim(CLAIM_AUTHORITY, authentication.getAuthorities())
                .claim(CLAIM_USERNAME, authentication.getName())
                .claim(CLAIM_PHONE, authenticateUser.getPhoneNumber())
                .claim(CLAIM_ROLE, authenticateUser.getRole())
                .subject(authenticateUser.getEmail())
                .expiration(validity)
                .compact(), validity.getTime());
    }

    private JwtBuilder defaultJwtBuilder() {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .signWith(secretKey, Jwts.SIG.HS256);
    }

    private JwtParserBuilder defaultJwtParserBuilder() {
        return Jwts.parser().verifyWith(secretKey);
    }

    public record TokenInfo(String token, Long validity) {
    }
}
