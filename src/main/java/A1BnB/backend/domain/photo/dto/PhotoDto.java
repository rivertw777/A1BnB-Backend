package A1BnB.backend.domain.photo.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class PhotoDto {

    // 추론 결과 요청
    public record InferenceResultRequest(@NotNull List<Long> photoIdList) {
    }

    // 사진 업로드 요청
    public record PhotoUploadRequest(@NotNull List<MultipartFile> photos) {
    }

    // 추론 결과 응답
    public record InferenceResultResponse(Long photoId, String roomType, String detectedUrl, List<String> amenityTypes) {
    }

    // 사진 정보
    public record PhotoInfo(String originalUrl, String detectedUrl, String roomType, List<String> amenityTypes) implements
            Serializable {
    }
}
