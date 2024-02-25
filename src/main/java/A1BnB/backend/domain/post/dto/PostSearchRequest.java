package A1BnB.backend.domain.post.dto;

import java.time.LocalDateTime;

public record PostSearchRequest(String authorName, String location, LocalDateTime checkIn, LocalDateTime checkOut,
                                String pricePerNight) {
    public PostSearchRequest {
    }
}
