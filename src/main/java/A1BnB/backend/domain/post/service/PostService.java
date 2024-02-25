package A1BnB.backend.domain.post.service;

import A1BnB.backend.domain.post.dto.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostSearchResponse;
import A1BnB.backend.domain.post.dto.PostUploadRequest;
import A1BnB.backend.domain.post.dto.PostResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void registerPost(String userName, PostUploadRequest uploadParam);
    List<PostResponse> getAllPosts();
    List<PostSearchResponse> searchByCondition(PostSearchRequest searchCondition);
}
