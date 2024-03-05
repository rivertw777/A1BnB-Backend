package A1BnB.backend.domain.postLike.service;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postLike.model.entity.PostLikeInfo;
import A1BnB.backend.domain.postLike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Transactional
    public void savePostLike(Post post, Member currentMember) {
        PostLikeInfo postLikeInfo = PostLikeInfo.builder()
                .post(post)
                .member(currentMember)
                .build();
        postLikeRepository.save(postLikeInfo);
    }

    @Transactional
    public void deletePostLike(Post post, Member currentMember) {
        postLikeRepository.deleteByPostAndMember(post, currentMember);
    }

}
