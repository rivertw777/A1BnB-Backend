package A1BnB.backend.domain.photo.dto;

import java.util.List;

// 사진 정보
public record PhotoInfo(String originalUrl, String detectedUrl, String roomType, List<String> amenityTypes) {
}

