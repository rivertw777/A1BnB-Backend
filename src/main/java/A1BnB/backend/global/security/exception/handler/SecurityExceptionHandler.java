package A1BnB.backend.global.security.exception.handler;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import A1BnB.backend.global.security.exception.ExpiredJwtTokenException;
import A1BnB.backend.global.exception.dto.CustomErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class SecurityExceptionHandler {

    // refresh 토큰 조회 실패
    @ExceptionHandler(ExpiredJwtTokenException.class)
    public ResponseEntity<CustomErrorResponse> handleExpiredJwtTokenException(ExpiredJwtTokenException e) {
        return ResponseEntity.status(FORBIDDEN).body(new CustomErrorResponse(e.getMessage()));
    }

}