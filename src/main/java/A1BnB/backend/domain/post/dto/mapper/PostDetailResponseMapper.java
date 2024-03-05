package A1BnB.backend.domain.post.dto.mapper;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.photo.dto.PhotoInfo;
import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postLike.repository.PostLikeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDetailResponseMapper {

    private final PostLikeRepository postLikeRepository;

    public PostDetailResponse toPostDetailResponse(Member currentMember, Post post, List<PhotoInfo> photoInfoList) {
        return new PostDetailResponse(
                post.getAuthor().getName(),
                photoInfoList,
                post.getLocation(),
                post.getAvailableDates(),
                post.getPricePerNight(),
                checkLike(post, currentMember),
                post.getMaximumOccupancy(),
                post.getCaption()
        );
    }

    private boolean checkLike(Post post, Member currentMember) {
        // 인증 X
        if (currentMember==null) {
            return false;
        }
        // 인증 O
        return postLikeRepository.findByPostAndMember(post, currentMember).isPresent();
    }
}
