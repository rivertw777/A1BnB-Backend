package A1BnB.backend.domain.post.dto;

import A1BnB.backend.domain.date.model.Date;
import A1BnB.backend.domain.photo.dto.PhotoDto.PhotoInfo;
import A1BnB.backend.domain.photo.model.Photo;
import A1BnB.backend.domain.post.dto.PostDto.PostDetailResponse;
import A1BnB.backend.domain.post.dto.PostDto.PostResponse;
import A1BnB.backend.domain.post.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PostDtoMapper {

    // 게시물 상세 응답 DTO 변환
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

    // 게시물 응답 DTO 변환
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
