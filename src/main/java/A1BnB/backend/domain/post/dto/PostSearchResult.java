package A1BnB.backend.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;

// 게시물 검색 결과
public record PostSearchResult(Long postId, String authorName, String location, LocalDateTime checkIn,
                               LocalDateTime checkOut, Double pricePerNight) {
    @QueryProjection
    public PostSearchResult(Long postId, String authorName, String location, LocalDateTime checkIn,
                            LocalDateTime checkOut, Double pricePerNight) {
        this.postId = postId;
        this.authorName = authorName;
        this.location = location;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.pricePerNight = pricePerNight;
    }
}