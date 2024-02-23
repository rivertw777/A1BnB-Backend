package A1BnB.backend.global.security.config.filter;

import A1BnB.backend.domain.member.dto.request.MemberLoginRequest;
import A1BnB.backend.global.security.utils.ResponseWriter;
import A1BnB.backend.global.security.model.CustomUserDetails;
import A1BnB.backend.global.security.dto.response.AccessTokenResponse;
import A1BnB.backend.global.security.service.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 인증
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final SecurityService securityService;
    @Autowired
    private final ResponseWriter responseWriter;

    // 인증 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            try {
                // 요청에서 로그인 정보 가져오기
                ObjectMapper om = new ObjectMapper();
                MemberLoginRequest loginParam = om.readValue(request.getInputStream(), MemberLoginRequest.class);
                // 인증 토큰 생성
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(loginParam.name(), loginParam.password());
                // 인증
                return authenticationManager.authenticate(authenticationToken);
            } catch (IOException e) {
                return null;
            }
    }

    // 인증 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        // authentication에서 userDetails 추출
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        // JWT 토큰 발급
        AccessTokenResponse accessTokenResponse = securityService.getAccessTokenResponse(userDetails);
        // response body에 access 토큰 DTO 담기
        responseWriter.writeAccessTokenResponse(response, accessTokenResponse);
    }

}