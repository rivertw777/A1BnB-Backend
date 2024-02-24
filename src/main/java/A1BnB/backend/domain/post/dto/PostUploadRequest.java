package A1BnB.backend.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PostUploadRequest(@NotNull List<String> photoPaths, @NotNull String location) {
    public PostUploadRequest {
    }
}
