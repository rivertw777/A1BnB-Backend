package A1BnB.backend.domain.photo.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record InferenceResultResponse(Long photoId, String roomType, String detectedUrl, List<String> amenityTypes) {
    public InferenceResultResponse {
    }
}
