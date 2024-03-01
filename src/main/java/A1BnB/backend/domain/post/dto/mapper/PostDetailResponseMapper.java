package A1BnB.backend.domain.post.dto.mapper;

import A1BnB.backend.domain.photo.dto.PhotoInfo;
import A1BnB.backend.domain.post.dto.PostDetailResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import java.util.List;
import org.springframework.stereotype.Component;
@Component
public class PostDetailResponseMapper {
    public PostDetailResponse toPostDetailResponse(Post post, List<PhotoInfo> photoInfoList) {
        return new PostDetailResponse(
                post.getAuthor().getName(),
                photoInfoList,
                post.getLocation(),
                post.getCheckIn(),
                post.getCheckOut(),
                post.getPricePerNight()
        );
    }
}
