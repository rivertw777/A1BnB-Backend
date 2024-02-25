package A1BnB.backend.domain.post.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(Long postId, String authorName, List<String> photoUrls, String location,
                           LocalDateTime checkIn, LocalDateTime checkOut, String pricePerNight) {
    public PostResponse {
    }
}