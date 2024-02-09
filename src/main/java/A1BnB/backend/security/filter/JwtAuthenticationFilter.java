package A1BnB.backend.security.filter;

import A1BnB.backend.member.dto.request.MemberLoginRequest;
import A1BnB.backend.security.dto.TokenData;
import A1BnB.backend.security.dto.response.TokenResponse;
import A1BnB.backend.security.model.CustomUserDetails;
import A1BnB.backend.security.utils.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 인증 필터
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

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
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    // 인증 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 토큰 생성
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();
        TokenData tokenData = tokenProvider.generateToken(userDetails);
        String accessToken = String.valueOf(tokenData.accessToken());
        TokenResponse tokenResponse = new TokenResponse(accessToken);

        // response body에 토큰 DTO 반환
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(tokenResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

}