package A1BnB.backend.global.security.config.handler;

import static A1BnB.backend.global.security.exception.SecurityExceptionMessages.LOGIN_FAILED;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import A1BnB.backend.global.security.utils.ResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

// 로그인 실패 (401)
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ResponseWriter responseWriter;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException authenticationException) throws IOException {
        responseWriter.writeErrorResponse(response, SC_UNAUTHORIZED, LOGIN_FAILED.getMessage());
    }
}
