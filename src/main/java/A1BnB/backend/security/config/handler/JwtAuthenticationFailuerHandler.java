package A1BnB.backend.security.config.handler;

import static A1BnB.backend.security.config.ResponseWriter.writeErrorResponse;
import static A1BnB.backend.security.exception.constants.SecurityExceptionMessages.LOGIN_FAILED;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

// 인증(로그인) 실패
@Component
public class JwtAuthenticationFailuerHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        writeErrorResponse(response, SC_UNAUTHORIZED, LOGIN_FAILED.getMessage());
    }
}
