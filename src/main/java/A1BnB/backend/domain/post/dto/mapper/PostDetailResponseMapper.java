package A1BnB.backend.domain.post.dto.mapper;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.photo.dto.PhotoInfo;
import A1BnB.backend.domain.post.dto.PostDetailResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.post.repository.PostLikeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostDetailResponseMapper {

    private final PostLikeRepository postLikeRepository;

    public PostDetailResponse toPostDetailResponse(Member currentMember, Post post, List<PhotoInfo> photoInfoList) {
        System.out.println(checkLike(post, currentMember));
        System.out.println(post.getPostId());
        System.out.println(currentMember);
        return new PostDetailResponse(
                post.getAuthor().getName(),
                photoInfoList,
                post.getLocation(),
                post.getCheckIn(),
                post.getCheckOut(),
                post.getPricePerNight(),
                checkLike(post, currentMember)
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
