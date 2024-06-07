package A1BnB.backend.domain.postLike.repository;

import A1BnB.backend.domain.member.model.Member;
import A1BnB.backend.domain.post.model.Post;
import A1BnB.backend.domain.postLike.model.PostLikeInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLikeInfo, Long> {
    Optional<PostLikeInfo> findByPostAndGuest(Post post, Member guest);
    List<PostLikeInfo> findByGuest(Member guest);
    void deleteByPostAndGuest(Post post, Member guest);
}