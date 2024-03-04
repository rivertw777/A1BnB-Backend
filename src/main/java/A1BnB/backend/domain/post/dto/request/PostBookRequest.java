package A1BnB.backend.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public record PostBookRequest(@NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkInDate,
                              @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime checkOutDate
                              ) {
    public PostBookRequest {
    }
}