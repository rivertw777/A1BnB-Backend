package A1BnB.backend.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

// 게시물 등록 요청
public record PostUploadRequest(@NotNull List<Long> photoIdList, @NotNull String location,
                                @NotNull String caption, @NotNull Integer pricePerNight,
                                @NotNull Integer maximumOccupancy) {
    public PostUploadRequest {
    }
}

