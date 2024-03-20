package A1BnB.backend.domain.member.dto.mapper;

import A1BnB.backend.domain.member.dto.response.HostPostResponse;
import A1BnB.backend.domain.member.dto.response.LikePostResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class LikePostResponseMapper {

    public List<LikePostResponse> toPostResponses(List<Post> posts) {
        return posts.stream()
                .map(post -> toPostResponse(post))
                .collect(Collectors.toList());
    }

    public LikePostResponse toPostResponse(Post post) {
        return new LikePostResponse(
                post.getId(),
                post.getHost().getName(),
                post.getPhotos().get(0).getOriginalUrl(),
                post.getLocation(),
                post.getPricePerNight()
        );
    }

}