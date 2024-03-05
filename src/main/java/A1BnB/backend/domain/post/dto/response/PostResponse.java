package A1BnB.backend.domain.post.dto.response;

import java.util.List;

// 게시물 응답
public record PostResponse(Long postId, String authorName, List<String> photoUrls, String location,
                           Double pricePerNight, Integer likeCount) {
    public PostResponse {
    }
}