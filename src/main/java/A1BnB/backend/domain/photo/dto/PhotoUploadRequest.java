package A1BnB.backend.domain.photo.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record PhotoUploadRequest(@NotNull List<MultipartFile> photos) {
    public PhotoUploadRequest {
    }
}

