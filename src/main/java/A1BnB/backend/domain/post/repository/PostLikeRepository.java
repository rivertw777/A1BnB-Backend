package A1BnB.backend.domain.post.repository;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.post.model.entity.PostLikeInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeInfo, Long> {
    Optional<PostLikeInfo> findByPostAndMember(Post post, Member member);

}