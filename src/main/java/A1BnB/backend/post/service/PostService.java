package A1BnB.backend.post.service;

import A1BnB.backend.post.dto.request.PostUploadRequest;
import A1BnB.backend.post.dto.response.PostResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void registerPost(Long memberId, PostUploadRequest uploadParam);
    List<PostResponse> getAllPosts(Long memberId);
}
