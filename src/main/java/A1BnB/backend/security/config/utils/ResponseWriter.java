package A1BnB.backend.security.config.utils;

import A1BnB.backend.exception.dto.CustomErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ResponseWriter {
    public static void writeErrorResponse(HttpServletResponse response, int httpStatus, String errorMessage) throws IOException {
        // Error DTO 생성
        CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
        // 상태 코드 설정
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(responseBody);
    }

}
