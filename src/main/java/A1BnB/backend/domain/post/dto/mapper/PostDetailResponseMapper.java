package A1BnB.backend.domain.post.dto.mapper;

import A1BnB.backend.domain.date.model.Date;
import A1BnB.backend.domain.photo.dto.PhotoDto.PhotoInfo;
import A1BnB.backend.domain.post.dto.PostDto.PostDetailResponse;
import A1BnB.backend.domain.post.model.Post;
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
                post.getHost().getName(),
                photoInfoList,
                post.getLocation(),
                getUnavailableDates(post),
                post.getPricePerNight(),
                post.getMaximumOccupancy(),
                post.getCaption()
        );
    }

    private List<LocalDateTime> getUnavailableDates(Post post) {
        return post.getPostBookInfos().stream()
                .flatMap(postBookInfo -> postBookInfo.getBookedDates().stream())
                .map(Date::getLocalDateTime)
                .collect(Collectors.toList());
    }

}
