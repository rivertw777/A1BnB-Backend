package A1BnB.backend.domain.post.dto.response;

import java.io.Serializable;
import java.util.List;

// 게시물 응답
public record PostResponse(Long postId, String hostName, List<String> photoUrls, String location,
                           Integer pricePerNight) implements Serializable {
    public PostResponse {
    }
}