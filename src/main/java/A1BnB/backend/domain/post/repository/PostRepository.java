package A1BnB.backend.domain.post.repository;

import A1BnB.backend.domain.post.model.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostSearchRepository {
    Post findByPostId(Long postId);

    @Query("SELECT p FROM Post p WHERE p.id IN :ids")
    List<Post> findAllByIdIn(@Param("ids") List<Long> ids);

}