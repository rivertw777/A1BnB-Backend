package A1BnB.backend.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

public record PostSearchResponse(String authorName, String location, LocalDateTime checkIn,
                                 LocalDateTime checkOut, Double pricePerNight) {
    @QueryProjection
    public PostSearchResponse(String authorName, String location, LocalDateTime checkIn,
                              LocalDateTime checkOut, Double pricePerNight) {
        this.authorName = authorName;
        this.location = location;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.pricePerNight = pricePerNight;
    }
}
