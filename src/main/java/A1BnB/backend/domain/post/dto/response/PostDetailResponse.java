package A1BnB.backend.domain.post.dto.response;

import A1BnB.backend.domain.photo.dto.PhotoInfo;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

// 게시물 상세 응답
public record PostDetailResponse(String authorName, List<PhotoInfo> photoInfoList, String location,
                                 List<LocalDateTime> availableDates, Double pricePerNight,
                                 boolean isLike, Integer maximumOccupancy, String caption) implements Serializable {
    public PostDetailResponse {
    }
}