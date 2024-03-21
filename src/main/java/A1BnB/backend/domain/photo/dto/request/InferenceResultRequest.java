package A1BnB.backend.domain.photo.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

// 추론 결과 요청
public record InferenceResultRequest(@NotNull List<Long> photoIdList) {
    public InferenceResultRequest {
    }
}
