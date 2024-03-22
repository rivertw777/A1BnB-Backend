package A1BnB.backend.domain.member.dto.response;


// 게시물 좋아요 게시물 응답
public record MyLikePostResponse(Long postId, String hostName, String thumbnail,
                                 String location, Integer pricePerNight) {
    public MyLikePostResponse {
    }
}