package A1BnB.backend.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

// 게시물 등록 요청
public record PostUploadRequest(@NotNull List<Long> photoIdList, @NotNull String location,
                                @NotNull String caption,
                                @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
                                @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate,
                                @NotNull Integer pricePerNight, @NotNull Integer maximumOccupancy) {
    public PostUploadRequest {
    }
}

