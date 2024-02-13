package A1BnB.backend.domain.security.config.handler;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import A1BnB.backend.domain.security.config.utils.ResponseWriter;
import A1BnB.backend.domain.security.exception.constants.SecurityExceptionMessages;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

// 인증 실패
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ResponseWriter.writeErrorResponse(response, SC_UNAUTHORIZED, SecurityExceptionMessages.LOGIN_FAILED.getMessage());
    }
}