package A1BnB.backend.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public record PostUploadRequest(@NotNull List<Long> photoIdList, @NotNull String location,
                                @NotNull String caption,
                                @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkIn,
                                @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkOut,
                                @NotNull Double pricePerNight) {
    public PostUploadRequest {
    }
}

