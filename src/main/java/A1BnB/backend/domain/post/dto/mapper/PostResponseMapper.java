package A1BnB.backend.domain.post.dto.mapper;

import A1BnB.backend.domain.post.dto.response.PostResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.photo.model.entity.Photo;
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
        List<String> photoUrls = post.getPhotos().stream()
                .map(Photo::getOriginalUrl)
                .collect(Collectors.toList());
        return new PostResponse(
                post.getPostId(),
                post.getAuthor().getName(),
                photoUrls,
                post.getLocation(),
                post.getPricePerNight(),
                post.getLikeCount()
        );
    }

}
