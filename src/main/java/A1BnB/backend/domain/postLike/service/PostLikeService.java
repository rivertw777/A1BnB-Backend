package A1BnB.backend.domain.postLike.service;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import org.springframework.stereotype.Service;

@Service
public interface PostLikeService {
    void savePostLike(Post post, Member currentMember);
    void deletePostLike(Post post, Member currentMember);
}
