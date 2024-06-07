package A1BnB.backend.domain.post.dto;

import A1BnB.backend.domain.photo.dto.PhotoDto.PhotoInfo;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class PostDto {

    // 게시물 예약 요청
    public record PostBookRequest(@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkInDate,
                                  @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkOutDate,
                                  @NotNull Integer paymentAmount
    ) {
    }

    // 게시물 검색 요청
    public record PostSearchRequest(String hostName, String location,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkInDate,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkOutDate,
                                    Integer minPrice, Integer maxPrice, List<String> amenities, Integer occupancy) {
    }

    // 게시물 등록 요청
    public record PostUploadRequest(@NotNull List<Long> photoIdList, @NotNull String location,
                                    @NotNull String caption, @NotNull Integer pricePerNight,
                                    @NotNull Integer maximumOccupancy) {
    }

    // 게시물 상세 응답
    public record PostDetailResponse(String hostName, List<PhotoInfo> photoInfoList, String location,
                                     List<LocalDateTime> unavailableDates, Integer pricePerNight,
                                     Integer maximumOccupancy, String caption) implements Serializable {
    }

    // 게시물 좋아요 확인 응답
    public record PostLikeCheckResponse (boolean isLike) {
    }

    // 게시물 좋아요 수 응답
    public record PostLikeCountResponse(Integer likeCount) {
    }

    // 게시물 응답
    public record PostResponse(Long postId, String hostName, List<String> photoUrls, String location,
                               Integer pricePerNight) implements Serializable {
    }

}
