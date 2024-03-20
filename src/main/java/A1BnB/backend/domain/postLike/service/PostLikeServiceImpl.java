package A1BnB.backend.domain.postLike.service;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postLike.model.entity.PostLikeInfo;
import A1BnB.backend.domain.postLike.repository.PostLikeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Override
    @Transactional
    public void likePost(Post post, Member currentMember) {
        PostLikeInfo postLikeInfo = PostLikeInfo.builder()
                .post(post)
                .guest(currentMember)
                .build();
        postLikeRepository.save(postLikeInfo);
    }

    @Override
    @Transactional
    public void unlikePost(Post post, Member currentMember) {
        postLikeRepository.deleteByPostAndGuest(post, currentMember);
    }

    @Override
    @Transactional
    public boolean findByPostAndMember(Post post, Member currentMember) {
        return postLikeRepository.findByPostAndGuest(post, currentMember).isPresent();
    }

    @Override
    public List<PostLikeInfo> findByMember(Member currentMember) {
        return postLikeRepository.findByGuest(currentMember);
    }

}
