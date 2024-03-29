package A1BnB.backend.domain.post.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

// 게시물 검색 조건 요청
public record PostSearchRequest(String hostName, String location,
                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkInDate,
                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkOutDate,
                                Integer minPrice, Integer maxPrice, List<String> amenities, Integer occupancy) {
    public PostSearchRequest {
    }
}
