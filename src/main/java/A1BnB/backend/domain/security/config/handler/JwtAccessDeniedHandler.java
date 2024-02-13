package A1BnB.backend.domain.security.config.handler;

import static A1BnB.backend.domain.security.exception.constants.SecurityExceptionMessages.NO_AUTHORITY;
import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import A1BnB.backend.domain.security.config.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

// 접근 권한이 없는 경우
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private final ResponseWriter responseWriter;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ResponseWriter.writeErrorResponse(response, SC_FORBIDDEN, NO_AUTHORITY.getMessage());
    }
}

