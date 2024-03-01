package A1BnB.backend.domain.post.repository;

import A1BnB.backend.domain.post.model.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostSearchRepository {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findByPostId(Long postId);

    @Query("SELECT p FROM Post p WHERE p.id IN :ids")
    Page<Post> findAllByIdList(@Param("ids") List<Long> ids, Pageable pageable);

}