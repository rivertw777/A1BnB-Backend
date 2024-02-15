package A1BnB.backend.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record PostUploadRequest(@NotNull MultipartFile[] photos, @NotNull String location) {
    public PostUploadRequest {
    }
}
