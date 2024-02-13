package A1BnB.backend.domain.post.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record PostUploadRequest(MultipartFile photo, String caption, String location) {
    public PostUploadRequest {
    }
}