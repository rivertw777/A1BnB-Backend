package A1BnB.backend.domain.postLike.service;

import A1BnB.backend.domain.member.model.Member;
import A1BnB.backend.domain.post.model.Post;
import A1BnB.backend.domain.postLike.model.PostLikeInfo;
import java.util.List;

public interface PostLikeService {
    void likePost(Post post, Member currentMember);
    void unlikePost(Post post, Member currentMember);
    boolean findByPostAndMember(Post post, Member currentMember);
    List<PostLikeInfo> findByMember(Member currentMember);
}
