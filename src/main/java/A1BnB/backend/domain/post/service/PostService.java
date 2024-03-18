package A1BnB.backend.domain.post.service;

import A1BnB.backend.domain.post.dto.request.PostBookRequest;
import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
import A1BnB.backend.domain.post.dto.response.PostLikeCheckResponse;
import A1BnB.backend.domain.post.dto.response.PostLikeCountResponse;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void registerPost(String username, PostUploadRequest requestParam);
    Page<PostResponse> getAllPosts(Pageable pageable);
    Page<PostResponse> searchByCondition(PostSearchRequest requestParam, Pageable pageable);
    PostDetailResponse getPostDetail(Long postId);
    PostLikeCheckResponse checkLike(String username, Long postId);
    void likePost(String username, Long postId);
    void unlikePost(String username, Long postId);
    void bookPost(String username, Long postId, PostBookRequest requestParam);
    void unbookPost(String username, Long postId);
    Page<PostResponse> getLikeRanking(Pageable pageable);
    PostLikeCountResponse getLikeCount(Long postId);
}
