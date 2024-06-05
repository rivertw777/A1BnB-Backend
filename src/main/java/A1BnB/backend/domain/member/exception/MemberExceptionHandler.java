package A1BnB.backend.domain.member.exception;

import A1BnB.backend.global.exception.dto.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class MemberExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<CustomErrorResponse> handleMemberException(MemberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(e.getMessage()));
    }

}