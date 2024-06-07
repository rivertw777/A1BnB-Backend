package A1BnB.backend.domain.post.repository;

import A1BnB.backend.domain.post.dto.PostDto.PostSearchRequest;
import A1BnB.backend.domain.post.model.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchRepository {
    Page<Post> search(PostSearchRequest requestParam, List<LocalDateTime> searchDates, Pageable pageable);
}
