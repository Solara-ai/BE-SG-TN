package org.se06203.besgtn.config.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.ErrorCode;
import org.se06203.besgtn.config.ApplicationConfigurationProperties;
import org.se06203.besgtn.config.exception.ServerException;
import org.se06203.besgtn.utils.Constants;
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

    private final ApplicationConfigurationProperties applicationConfig;

    private static final String CLAIM_AUTHORITY = "auth";
    private static final String CLAIM_USER_ID = "user_id";
    private static final String CLAIM_NAME = "name";
    private static final String CLAIM_PHONE = "phone";
    private static final String CLAIM_EMAIL = "email";

    @PostConstruct
    public void init() {
        String secret = applicationConfig.getSecurity().getAuthentication().getJwt().getBase64Secret();
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
    public Constants.AuthorityEnum extractAuthority(String token) {
        var refType = new TypeReference<Set<HashMap<String, String>>>() {
        };
        var authorities = new ObjectMapper().convertValue(extractClaim(token, claims -> claims.get(CLAIM_AUTHORITY, Object.class)), refType);
        var authority = authorities.stream()
                .findFirst()
                .orElseThrow(() -> new ServerException(ErrorCode.UNAUTHORIZED));
        return Constants.AuthorityEnum.valueOf(authority.get("authority"));
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

    public String getLoginFromToken(String token) {
        return this.defaultJwtParserBuilder()
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public TokenInfo createToken(Authentication authentication) {
        return this.createToken(authentication, false);
    }

    public String createRefreshToken(Authentication authentication) {
        var jwtConfig = applicationConfig.getSecurity().getAuthentication().getJwt();
        Date validity = new Date(System.currentTimeMillis() +
                (jwtConfig.getRefreshTokenValidityInSeconds() * 1000));

        return this.defaultJwtBuilder()
                .subject(authentication.getName())
                .expiration(validity)
                .compact();
    }

    public TokenInfo createToken(Authentication authentication, boolean rememberMe) {
        var jwtConfig = applicationConfig.getSecurity().getAuthentication().getJwt();
        var authenticateUser = (SpringSecurityUser) authentication.getPrincipal();
        Date validity = new Date(System.currentTimeMillis() +
                (rememberMe
                        ? jwtConfig.getTokenValidityInSecondsForRememberMe()
                        : jwtConfig.getTokenValidityInSeconds()) * 1000);

        return new TokenInfo(this.defaultJwtBuilder()
                .claim(CLAIM_AUTHORITY, authentication.getAuthorities())
                .claim(CLAIM_NAME, authenticateUser.getName())
                .claim(CLAIM_EMAIL, authenticateUser.getEmail())
                .claim(CLAIM_PHONE, authenticateUser.getPhoneNumber())
                .claim(CLAIM_USER_ID, authenticateUser.getId())
                .subject(authentication.getName())
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

    public String getRoleFromToken(String bearerToken) {
        return this.extractAuthority(bearerToken).name();
    }

    public record TokenInfo(String token, Long validity) {
    }
}
