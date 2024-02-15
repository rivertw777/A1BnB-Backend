package A1BnB.backend.domain.post.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record PostUploadRequest(@NotNull List<MultipartFile> photos, @NotNull String location) {
    public PostUploadRequest {
    }
}
