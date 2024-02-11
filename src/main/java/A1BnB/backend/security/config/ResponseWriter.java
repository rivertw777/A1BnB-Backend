package A1BnB.backend.security.config;

import A1BnB.backend.exception.dto.CustomErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class ResponseWriter {
    public static void writeErrorResponse(HttpServletResponse response, int httpStatus, String errorMessage) throws IOException {
        // 상태 코드 설정
        response.setStatus(httpStatus);
        ObjectMapper objectMapper = new ObjectMapper();
        CustomErrorResponse errorResponse = new CustomErrorResponse(errorMessage);
        // response body에 에러 메시지 저장
        String responseBody = objectMapper.writeValueAsString(errorResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

}
