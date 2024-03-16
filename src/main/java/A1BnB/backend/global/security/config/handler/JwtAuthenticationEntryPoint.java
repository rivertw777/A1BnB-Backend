package A1BnB.backend.global.security.config.handler;

import static A1BnB.backend.global.exception.constants.SecurityExceptionMessages.UNAUTHORIZED;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import A1BnB.backend.global.security.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

// 인증 실패 (401)
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ResponseWriter responseWriter;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authenticationException) throws IOException {
        //responseWriter.writeErrorResponse(response, SC_UNAUTHORIZED, UNAUTHORIZED.getMessage());
        response.setStatus(SC_UNAUTHORIZED);
    }
}