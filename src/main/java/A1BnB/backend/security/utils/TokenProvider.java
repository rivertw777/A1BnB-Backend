package A1BnB.backend.security.utils;

import A1BnB.backend.security.dto.TokenData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
                .claim("auth", userDetails.getAuthorities())
                .setExpiration(new Date(now + accessTokenExpiration))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();

        // refresh 토큰 생성
        String refreshToken = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(now + refreshTokenExpiration))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
        return new TokenData(accessToken, refreshToken);
    }

    // 토큰 복호화
    public Claims parseClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}