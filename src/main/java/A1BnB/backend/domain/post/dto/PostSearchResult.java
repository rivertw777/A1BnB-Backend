package A1BnB.backend.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;

// 게시물 검색 결과
public record PostSearchResult(Long postId, String authorName, String location, Double pricePerNight,
                               Integer maximumOccupancy) {
    @QueryProjection
    public PostSearchResult(Long postId, String authorName, String location, Double pricePerNight,
                            Integer maximumOccupancy) {
        this.postId = postId;
        this.authorName = authorName;
        this.location = location;
        this.pricePerNight = pricePerNight;
        this.maximumOccupancy = maximumOccupancy;
    }
}
