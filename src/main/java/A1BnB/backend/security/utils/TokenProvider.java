package A1BnB.backend.security.utils;

import A1BnB.backend.redis.service.RedisService;
import A1BnB.backend.security.dto.TokenData;
import A1BnB.backend.security.model.CustomUserDetails;
import A1BnB.backend.security.service.SecurityService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private final SecurityService securityService;
    private final RedisService redisService;
    private final Key jwtSecretKey;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public TokenProvider(SecurityService securityService,
                         RedisService redisService, @Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access.expiration}") String accessTokenExpiration,
                         @Value("${jwt.refresh.expiration}") String refreshTokenExpiration){
        this.securityService = securityService;
        this.redisService = redisService;
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

    // 인증 정보 추출
    public Authentication extractAuthentication(String token) {
        // 토큰 복호화
        Claims claims = parseClaims(token);
        if (claims.get("auth") == null) {
            throw new IllegalArgumentException("권한 정보가 없는 토큰입니다.");
        }

        // 회원 이름 추출
        String name = claims.getSubject();

        // UserDetails 객체를 만들어서 Authentication 리턴
        CustomUserDetails customUserDetails = securityService.loadUserByUsername(name);
        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }

    // 토큰 복호화
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // 토큰 만료시 예외 처리
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}