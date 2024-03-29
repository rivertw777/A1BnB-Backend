package A1BnB.backend.domain.photo.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

// 사진 업로드 요청
public record PhotoUploadRequest(@NotNull List<MultipartFile> photos) {
    public PhotoUploadRequest {
    }
}

