package A1BnB.backend.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

// 게시물 검색 조건 요청
public record PostSearchRequest(String authorName, String location,
                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkIn,
                                @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkOut,
                                Double minPrice, Double maxPrice, List<String> amenities ) {
    public PostSearchRequest {
    }
}
