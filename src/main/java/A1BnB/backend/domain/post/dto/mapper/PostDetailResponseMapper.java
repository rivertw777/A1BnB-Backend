package A1BnB.backend.domain.post.dto.mapper;

import A1BnB.backend.domain.date.model.entity.Date;
import A1BnB.backend.domain.photo.dto.PhotoInfo;
import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDetailResponseMapper {

    public PostDetailResponse toPostDetailResponse(Post post, List<PhotoInfo> photoInfoList) {
        return new PostDetailResponse(
                post.getAuthor().getName(),
                photoInfoList,
                post.getLocation(),
                getLocalDateTimeDates(post),
                post.getPricePerNight(),
                post.getMaximumOccupancy(),
                post.getCaption()
        );
    }

    private List<LocalDateTime> getLocalDateTimeDates(Post post) {
        return post.getAvailableDates().stream()
                .map(Date::getLocalDateTime)
                .collect(Collectors.toList());
    }

}
