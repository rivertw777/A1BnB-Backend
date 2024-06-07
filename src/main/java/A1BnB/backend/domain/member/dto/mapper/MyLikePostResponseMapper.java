package A1BnB.backend.domain.member.dto.mapper;

import A1BnB.backend.domain.member.dto.MemberDto.MyLikePostResponse;
import A1BnB.backend.domain.post.model.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class MyLikePostResponseMapper {

    public List<MyLikePostResponse> toPostResponses(List<Post> posts) {
        return posts.stream()
                .map(post -> toPostResponse(post))
                .collect(Collectors.toList());
    }

    public MyLikePostResponse toPostResponse(Post post) {
        return new MyLikePostResponse(
                post.getId(),
                post.getHost().getName(),
                post.getPhotos().get(0).getOriginalUrl(),
                post.getLocation(),
                post.getPricePerNight()
        );
    }

}