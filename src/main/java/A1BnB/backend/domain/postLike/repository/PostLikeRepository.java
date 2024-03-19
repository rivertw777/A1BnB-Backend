package A1BnB.backend.domain.postLike.repository;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postLike.model.entity.PostLikeInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLikeInfo, Long> {
    Optional<PostLikeInfo> findByPostAndMember(Post post, Member member);
    List<PostLikeInfo> findByMember(Member member);
    void deleteByPostAndMember(Post post, Member member);
}