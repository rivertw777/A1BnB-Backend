package A1BnB.backend.security.service;

import static A1BnB.backend.member.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.member.model.entity.Member;
import A1BnB.backend.redis.service.RedisService;
import A1BnB.backend.repository.MemberRepository;
import A1BnB.backend.security.dto.TokenData;
import A1BnB.backend.security.dto.response.TokenResponse;
import A1BnB.backend.security.model.CustomUserDetails;
import A1BnB.backend.security.utils.TokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Transactional
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

    public TokenResponse getTokenResponse(CustomUserDetails userDetails) {

        TokenData tokenData = tokenProvider.generateToken(userDetails);

        // redis에 refresh 토큰 저장
        String refreshToken = tokenData.refreshToken();
        long expiration = tokenProvider.parseClaims(refreshToken).getExpiration().getTime();
        redisService.setToken(refreshToken, userDetails.getUsername(), expiration);

        // access 토큰 DTO 반환
        return new TokenResponse(tokenData.accessToken());
    }

    public Authentication getAuthentication(String token){

        return extractAuthentication(token);
    }

    // 인증 정보 추출
    private Authentication extractAuthentication(String token) throws ExpiredJwtException {
        // 토큰 복호화
        Claims claims = tokenProvider.parseClaims(token);
        if (claims.get("auth") == null) {
            throw new IllegalArgumentException("권한 정보가 없는 토큰입니다.");
        }

        // 회원 이름 추출
        String name = claims.getSubject();

        // UserDetails 객체를 만들어서 Authentication 리턴
        CustomUserDetails customUserDetails = loadUserByUsername(name);
        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }
}