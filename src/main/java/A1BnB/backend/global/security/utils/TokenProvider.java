package A1BnB.backend.global.security.utils;

import static A1BnB.backend.global.security.exception.constants.SecurityExceptionMessages.EXPIRED_ACCESS_TOKEN;
import static A1BnB.backend.global.security.exception.constants.SecurityExceptionMessages.MALFORMED_TOKEN;
import static A1BnB.backend.global.security.exception.constants.SecurityExceptionMessages.UNSUPPORTED_TOKEN;

import A1BnB.backend.global.security.dto.TokenData;
import A1BnB.backend.global.security.exception.ExpiredJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final Key jwtSecretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    private static final String AUTHORITIES_KEY = "auth";

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access.expiration}") String accessTokenExpiration,
                         @Value("${jwt.refresh.expiration}") String refreshTokenExpiration) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = Long.parseLong(accessTokenExpiration);
        this.refreshTokenExpiration = Long.parseLong(refreshTokenExpiration);
    }

    // 토큰 생성
    public TokenData generateToken(UserDetails userDetails) {
        long now = (new Date()).getTime();

        // access 토큰 생성
        String accessToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES_KEY, userDetails.getAuthorities())
                .setExpiration(new Date(now + accessTokenExpiration))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();

        // refresh 토큰 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenExpiration))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
        return new TokenData(accessToken, refreshToken);
    }

    // 토큰 복호화
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 검증
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token);
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException(MALFORMED_TOKEN.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtTokenException(EXPIRED_ACCESS_TOKEN.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(UNSUPPORTED_TOKEN.getMessage());
        }
    }
}