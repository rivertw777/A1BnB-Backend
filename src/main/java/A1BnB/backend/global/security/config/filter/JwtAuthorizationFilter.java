package A1BnB.backend.global.security.config.filter;

import static A1BnB.backend.global.security.constants.JwtProperties.HEADER_STRING;
import static A1BnB.backend.global.security.constants.JwtProperties.TOKEN_PREFIX;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import A1BnB.backend.global.security.dto.AccessTokenResponse;
import A1BnB.backend.global.security.utils.ResponseWriter;
import A1BnB.backend.global.security.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// 인가 필터
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final SecurityService securityService;
    private final ResponseWriter responseWriter;

    private final String TOKEN_REFRESH_REQUEST_URI = "/api/security/refresh";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 헤더에 토큰 정보 확인
        String header = request.getHeader(HEADER_STRING.getValue());
        if (header == null || !header.startsWith(TOKEN_PREFIX.getValue())) {
            chain.doFilter(request, response);
            return;
        }
        // 헤더에서 토큰 정보 추출
        String token = request.getHeader(HEADER_STRING.getValue()).replace(TOKEN_PREFIX.getValue(), "");

        // 토큰 검증 및 인가
        try {
            securityService.validateToken(token);
            // 인증 정보 추출
            Authentication authentication = securityService.extractAuthentication(token);
            // 사용자 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // access 토큰 만료시
        catch (SecurityException e){
            // 토큰 재발급 요청 여부 확인
            if (checkRefreshRequest(request, response, token)) {
                return;
            }
            responseWriter.writeErrorResponse(response, SC_UNAUTHORIZED, e.getMessage());
        }
        // 나머지 예외 상황
        catch (Exception e) {
            responseWriter.writeErrorResponse(response, SC_UNAUTHORIZED, e.getMessage());
        }
        chain.doFilter(request, response);
   }

   private boolean checkRefreshRequest(HttpServletRequest request, HttpServletResponse response, String token)
           throws IOException {
       String currentPath = request.getRequestURI();
       if (TOKEN_REFRESH_REQUEST_URI.equals(currentPath)) {
           try {
               // 토큰 재발급
               AccessTokenResponse accessTokenResponse = securityService.refreshAccessToken(token);
               responseWriter.writeAccessTokenResponse(response, accessTokenResponse);
               return true;
           // refresh 토큰 만료시
           } catch (SecurityException e) {
               responseWriter.writeErrorResponse(response, SC_UNAUTHORIZED, e.getMessage());
               return true;
           }
       }
       return false;
    }
}