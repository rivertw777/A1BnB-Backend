package A1BnB.backend.domain.security.service;

import static A1BnB.backend.domain.member.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.security.dto.TokenData;
import A1BnB.backend.domain.security.dto.response.AccessTokenResponse;
import A1BnB.backend.domain.security.exception.ExpiredJwtTokenException;
import A1BnB.backend.domain.security.model.CustomUserDetails;
import A1BnB.backend.domain.security.utils.TokenProvider;
import A1BnB.backend.domain.redis.service.RedisService;
import A1BnB.backend.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;

    // 이름으로 회원 조회
    @Override
    public CustomUserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));
        return new CustomUserDetails(member);
    }

    // 토큰 DTO 반환
    public AccessTokenResponse getAccessTokenResponse(CustomUserDetails userDetails) {
        // 토큰 생성
        TokenData tokenData = tokenProvider.generateToken(userDetails);
        // redis에 refresh 토큰 저장
        setRefreshTokenInRedis(tokenData);
        // access 토큰 DTO 반환
        return new AccessTokenResponse(tokenData.accessToken());
    }

    // redis에 refresh 토큰 저장
    @Transactional
    private void setRefreshTokenInRedis(TokenData tokenData){
        String accessToken = tokenData.accessToken();
        String userName = tokenProvider.parseClaims(accessToken).getSubject();
        String refreshToken = tokenData.refreshToken();
        long expiration = tokenProvider.parseClaims(refreshToken).getExpiration().getTime();
        redisService.setToken(userName, refreshToken, expiration);
    }

    // 인증 정보 반환
    public Authentication extractAuthentication(String token) {
        // 토큰 복호화
        Claims claims = tokenProvider.parseClaims(token);
        // 회원 이름 추출
        String userName = claims.getSubject();
        // userDetails 조회
        CustomUserDetails userDetails = loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰 검증
    public void validateToken(String token)
            throws MalformedJwtException, ExpiredJwtTokenException, UnsupportedJwtException {
        tokenProvider.validateToken(token);
    }

    public AccessTokenResponse refreshAccessToken(String accessToken) {
        // redis에서 refresh 토큰 조회
        String userName = tokenProvider.parseClaims(accessToken).getSubject();
        String refreshToken = redisService.getRefreshToken(userName);
        if (refreshToken == null) {
            // 재로그인 요청
        }
        CustomUserDetails userDetails = loadUserByUsername(userName);
        // 토큰 DTO 반환
        return getAccessTokenResponse(userDetails);
    }

    // 로그아웃
    @Transactional
    public void logout(String accessToken){
        String username = tokenProvider.parseClaims(accessToken).getSubject();
        redisService.deleteToken(username);
    }

}