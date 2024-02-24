package A1BnB.backend.global.security.service;

import static A1BnB.backend.domain.member.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.global.security.dto.TokenData;
import A1BnB.backend.global.security.dto.AccessTokenResponse;
import A1BnB.backend.global.security.exception.ExpiredJwtTokenException;
import A1BnB.backend.global.security.model.CustomUserDetails;
import A1BnB.backend.global.security.utils.TokenProvider;
import A1BnB.backend.global.redis.service.RedisService;
import A1BnB.backend.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
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
        // 이름 추출
        String userName = tokenProvider.parseClaims(accessToken).getSubject();
        String refreshToken = tokenData.refreshToken();
        // 만료 시간 추출
        long expiration = tokenProvider.parseClaims(refreshToken).getExpiration().getTime();
        redisService.setRefreshToken(userName, refreshToken, expiration);
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

    public AccessTokenResponse refreshAccessToken(String accessToken) throws ExpiredJwtTokenException {
        // redis에서 refresh 토큰 조회
        String userName = tokenProvider.parseClaims(accessToken).getSubject();
        String refreshToken = redisService.getRefreshToken(userName);
        // 조회 실패시 예외 처리
        if (refreshToken == null) {
            throw new ExpiredJwtTokenException("만료된 JWT 토큰입니다.");
        }
        // 조회 성공시 토큰 재발급
        CustomUserDetails userDetails = loadUserByUsername(userName);
        return getAccessTokenResponse(userDetails);
    }

    // 로그아웃
    @Transactional
    public void logout(String username){
        redisService.deleteRefreshToken(username);
    }

}