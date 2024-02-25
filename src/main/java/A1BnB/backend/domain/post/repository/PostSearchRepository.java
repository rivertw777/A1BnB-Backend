package A1BnB.backend.domain.post.repository;

import A1BnB.backend.domain.post.dto.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostSearchResponse;
import java.util.List;

public interface PostSearchRepository {
    List<PostSearchResponse> search(PostSearchRequest searchCondition);
}
