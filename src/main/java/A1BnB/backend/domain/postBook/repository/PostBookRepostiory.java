package A1BnB.backend.domain.postBook.repository;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBookRepostiory extends  JpaRepository<PostBookInfo, Long> {
    Optional<PostBookInfo> findByPostAndMember(Post post, Member member);
    void deleteByPostAndMember(Post post, Member member);
}
