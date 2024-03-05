package A1BnB.backend.domain.postLike.service;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostLikeService {
    void likePost(Post post, Member currentMember);
    void unlikePost(Post post, Member currentMember);
}
