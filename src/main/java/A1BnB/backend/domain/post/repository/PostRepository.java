package A1BnB.backend.domain.post.repository;

import A1BnB.backend.domain.post.model.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostSearchRepository {

    Page<Post> findAll(Pageable pageable);

    Optional<Post> findById(Long id);

    List<Post> findAllByIdIn(List<Long> Ids);

}