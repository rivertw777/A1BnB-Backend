package A1BnB.backend.post.dto.response.mapper;

import A1BnB.backend.post.dto.response.PostResponse;
import A1BnB.backend.post.model.Post;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostResponseMapper {

    public List<PostResponse> toPostResponses(List<Post> posts) {
        return posts.stream()
                .map(post -> toPostResponse(post))
                .collect(Collectors.toList());
    }

    public PostResponse toPostResponse(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getPhotoUrl()
        );
    }

}
