package A1BnB.backend.domain.post.dto;

import java.util.List;

public record PostResponse(Long postId, List<String> photoUrls, String location) {
    public PostResponse{
    }
}