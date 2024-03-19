package A1BnB.backend.domain.member.dto.mapper;

import A1BnB.backend.domain.member.dto.response.HostPostResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class HostPostResponseMapper {

    public List<HostPostResponse> toPostResponses(List<Post> posts) {
        return posts.stream()
                .map(post -> toPostResponse(post))
                .collect(Collectors.toList());
    }

    public HostPostResponse toPostResponse(Post post) {
        return new HostPostResponse(
                post.getId(),
                post.getCreatedAt(),
                post.getHost().getName(),
                post.getPhotos().get(0).getOriginalUrl(),
                post.getLocation(),
                post.getPricePerNight(),
                post.getPostBookInfos().size() > 0
        );
    }

}