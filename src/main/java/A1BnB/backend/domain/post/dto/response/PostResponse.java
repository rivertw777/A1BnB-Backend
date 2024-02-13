package A1BnB.backend.domain.post.dto.response;

public record PostResponse(Long postId, String photoUrl, String location) {
    public PostResponse{
    }
}