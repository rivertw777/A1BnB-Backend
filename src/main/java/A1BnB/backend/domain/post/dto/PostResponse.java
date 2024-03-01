package A1BnB.backend.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

// 게시물 응답
public record PostResponse(Long postId, String authorName, List<String> photoUrls, String location,
                           LocalDateTime checkIn, LocalDateTime checkOut, Double pricePerNight) {
    public PostResponse {
    }
}