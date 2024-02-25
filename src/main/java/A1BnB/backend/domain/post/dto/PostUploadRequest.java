package A1BnB.backend.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record PostUploadRequest(@NotNull List<Long> photoIdList, @NotNull String location,
                                @NotNull String caption, @NotNull LocalDateTime checkIn,
                                @NotNull LocalDateTime checkOut, @NotNull String pricePerNight) {
    public PostUploadRequest {
    }
}

