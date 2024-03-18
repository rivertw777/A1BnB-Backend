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

    @Override
    @Transactional
    public void likePost(Post post, Member currentMember) {
        PostLikeInfo postLikeInfo = PostLikeInfo.builder()
                .post(post)
                .member(currentMember)
                .build();
        postLikeRepository.save(postLikeInfo);
    }

    @Override
    @Transactional
    public void unlikePost(Post post, Member currentMember) {
        postLikeRepository.deleteByPostAndMember(post, currentMember);
    }

    @Override
    @Transactional
    public boolean findByPostAndMember(Post post, Member currentMember) {
        return postLikeRepository.findByPostAndMember(post, currentMember).isPresent();
    }

}
