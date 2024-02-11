package A1BnB.backend.security.config.filter;


import static A1BnB.backend.security.constants.JwtProperties.HEADER_STRING;
import static A1BnB.backend.security.constants.JwtProperties.TOKEN_PREFIX;

import A1BnB.backend.security.service.SecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

// 인가 필터
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private final SecurityService securityService;

    public JwtAuthorizationFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

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

        // 인증 정보 반환
        Authentication authentication = securityService.getAuthentication(token);

        // 시큐리티의 세션에 접근하여 값 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}