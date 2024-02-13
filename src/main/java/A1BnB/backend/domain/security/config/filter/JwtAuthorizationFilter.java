package A1BnB.backend.domain.security.config.filter;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import A1BnB.backend.domain.security.config.utils.ResponseWriter;
import A1BnB.backend.domain.security.constants.JwtProperties;
import A1BnB.backend.domain.security.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

// 인가 필터
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private final SecurityService securityService;
    @Autowired
    private final ResponseWriter responseWriter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 헤더에 토큰 정보 확인
        String header = request.getHeader(JwtProperties.HEADER_STRING.getValue());
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX.getValue())) {
            chain.doFilter(request, response);
            return;
        }
        // 헤더에서 토큰 정보 추출
        String token = request.getHeader(JwtProperties.HEADER_STRING.getValue()).replace(JwtProperties.TOKEN_PREFIX.getValue(), "");

        // 토큰 검증 및 인가
        try {
            securityService.validateToken(token);
            // 인증 정보 추출
            Authentication authentication = securityService.extractAuthentication(token);
            // 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(authentication.getPrincipal());
            System.out.println(authentication.getName());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println(userDetails.getUsername());
        } catch (Exception e) {
            responseWriter.writeErrorResponse(response, SC_FORBIDDEN, e.getMessage());
        }
        chain.doFilter(request, response);

   }
}