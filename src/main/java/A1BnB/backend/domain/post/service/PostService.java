package A1BnB.backend.domain.post.service;

import A1BnB.backend.domain.post.dto.PostUploadRequest;
import A1BnB.backend.domain.post.dto.PostResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void registerPost(String userName, PostUploadRequest uploadParam) throws IOException;
    List<PostResponse> getAllPosts();
}
