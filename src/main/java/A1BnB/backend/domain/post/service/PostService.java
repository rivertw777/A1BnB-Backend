package A1BnB.backend.domain.post.service;

import A1BnB.backend.domain.post.dto.PostDetailResponse;
import A1BnB.backend.domain.post.dto.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostUploadRequest;
import A1BnB.backend.domain.post.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void registerPost(String userName, PostUploadRequest uploadParam);
    Page<PostResponse> getAllPosts(Pageable pageable);
    Page<PostResponse> searchByCondition(PostSearchRequest searchCondition, Pageable pageable);
    PostDetailResponse getPostDetail(Long postId);
}
