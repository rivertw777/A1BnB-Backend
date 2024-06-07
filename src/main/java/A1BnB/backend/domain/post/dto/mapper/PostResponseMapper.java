package A1BnB.backend.domain.post.dto.mapper;

import A1BnB.backend.domain.post.dto.PostDto.PostResponse;
import A1BnB.backend.domain.post.model.Post;
import A1BnB.backend.domain.photo.model.Photo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostResponseMapper {

    public PostResponse toPostResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getHost().getName(),
                getPhotoUrls(post),
                post.getLocation(),
                post.getPricePerNight()
        );
    }

    private List<String> getPhotoUrls(Post post) {
        return post.getPhotos().stream()
                .map(Photo::getOriginalUrl)
                .collect(Collectors.toList());
    }

}
