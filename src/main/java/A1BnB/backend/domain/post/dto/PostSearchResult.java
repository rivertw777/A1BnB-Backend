package A1BnB.backend.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;

// 게시물 검색 결과
public record PostSearchResult(Long postId, String authorName, String location, Double pricePerNight,
                               List<LocalDateTime> availableDates, Integer maximumOccupancy) {
    @QueryProjection
    public PostSearchResult(Long postId, String authorName, String location, Double pricePerNight,
                            List<LocalDateTime> availableDates, Integer maximumOccupancy) {
        this.postId = postId;
        this.authorName = authorName;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.availableDates = availableDates;
        this.maximumOccupancy = maximumOccupancy;
    }
}
