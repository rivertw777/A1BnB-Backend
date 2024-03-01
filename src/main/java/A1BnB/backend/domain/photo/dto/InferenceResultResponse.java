package A1BnB.backend.domain.photo.dto;

import java.util.List;

// 추론 결과 응답
public record InferenceResultResponse(Long photoId, String roomType, String detectedUrl, List<String> amenityTypes) {
    public InferenceResultResponse {
    }
}
