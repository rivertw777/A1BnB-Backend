package A1BnB.backend.domain.post.repository;

import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostSearchResult;
import java.time.LocalDateTime;
import java.util.List;

public interface PostSearchRepository {

    List<PostSearchResult> search(PostSearchRequest requestParam, List<LocalDateTime> searchDates);
}
