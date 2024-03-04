package A1BnB.backend.domain.post.service;

import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void registerPost(String userName, PostUploadRequest requestParam);
    Page<PostResponse> getAllPosts(Pageable pageable);
    Page<PostResponse> searchByCondition(PostSearchRequest searchCondition, Pageable pageable);
    PostDetailResponse getPostDetail(String username, Long postId);
    void likePost(String username, Long postId);
    void unlikePost(String username, Long postId);
}
