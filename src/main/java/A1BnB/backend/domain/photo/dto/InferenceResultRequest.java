package A1BnB.backend.domain.photo.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record InferenceResultRequest(@NotNull List<Long> photoIdList) {
    public InferenceResultRequest {
    }
}
