package A1BnB.backend.global.exception.handler;

import A1BnB.backend.global.exception.PostException;
import A1BnB.backend.global.exception.dto.CustomErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class PostExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ResponseEntity<CustomErrorResponse> handleMemberException(PostException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(e.getMessage()));
    }

}