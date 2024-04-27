package A1BnB.backend.global.security.service;

import static A1BnB.backend.global.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;
import static A1BnB.backend.global.exception.constants.SecurityExceptionMessages.EXPIRED_REFRESH_TOKEN;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.global.security.dto.TokenData;
import A1BnB.backend.global.security.dto.response.AccessTokenResponse;
import A1BnB.backend.global.exception.SecurityException;
import A1BnB.backend.global.security.model.CustomUserDetails;
import A1BnB.backend.global.security.utils.TokenProvider;
import A1BnB.backend.global.redis.service.RefreshTokenService;
import A1BnB.backend.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.refresh.expiration}")
    private String refreshTokenExpiration;

    // 이름으로 회원 조회
    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        Member member = memberRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));
        return new CustomUserDetails(member);
    }

    // Access 토큰 DTO 반환
    public AccessTokenResponse getAccessTokenResponse(CustomUserDetails userDetails) {
        // 토큰 생성
        TokenData tokenData = tokenProvider.generateToken(userDetails);
        setRefreshTokenInRedis(userDetails.getUsername(), tokenData);
        return new AccessTokenResponse(tokenData.accessToken());
    }

    // redis에 refresh 토큰 저장
    @Transactional
    private void setRefreshTokenInRedis(String username, TokenData tokenData){
        String refreshToken = tokenData.refreshToken();
        refreshTokenService.setRefreshToken(username, refreshToken, Long.valueOf(refreshTokenExpiration));
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
    public void validateToken(String token) {
        tokenProvider.validateToken(token);
    }

    public AccessTokenResponse refreshAccessToken(String accessToken) {
        // redis에서 refresh 토큰 조회
        String userName = tokenProvider.parseClaims(accessToken).getSubject();
        String refreshToken = refreshTokenService.getRefreshToken(userName);
        // 조회 실패시 예외 처리
        if (refreshToken == null) {
            throw new SecurityException(EXPIRED_REFRESH_TOKEN.getMessage());
        }
        // 조회 성공시 토큰 재발급
        CustomUserDetails userDetails = loadUserByUsername(userName);
        return getAccessTokenResponse(userDetails);
    }

    // 로그아웃
    public void logout(String username){
        refreshTokenService.deleteRefreshToken(username);
    }

}