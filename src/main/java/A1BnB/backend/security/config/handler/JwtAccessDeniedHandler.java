package A1BnB.backend.security.config.handler;

import static A1BnB.backend.security.config.ResponseWriter.writeErrorResponse;
import static A1BnB.backend.security.exception.constants.SecurityExceptionMessages.NO_AUTHORITY;
import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

// 접근 권한이 없는 경우
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        writeErrorResponse(response, SC_FORBIDDEN, NO_AUTHORITY.getMessage());
    }
}

