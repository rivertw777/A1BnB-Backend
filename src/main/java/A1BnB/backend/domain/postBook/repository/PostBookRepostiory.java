package A1BnB.backend.domain.postBook.repository;

import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostBookRepostiory extends  JpaRepository<PostBookInfo, Long> {
    List<PostBookInfo> findByGuest(Member guest);
    @Query("SELECT pbi FROM PostBookInfo pbi WHERE pbi.post IN :posts")
    List<PostBookInfo> findByPosts(@Param("posts") List<Post> posts);
}
