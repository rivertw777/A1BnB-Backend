package A1BnB.backend.domain.post.service;

import A1BnB.backend.domain.post.dto.PostDto.PostBookRequest;
import A1BnB.backend.domain.post.dto.PostDto.PostDetailResponse;
import A1BnB.backend.domain.post.dto.PostDto.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostDto.PostUploadRequest;
import A1BnB.backend.domain.post.dto.PostDto.PostLikeCheckResponse;
import A1BnB.backend.domain.post.dto.PostDto.PostLikeCountResponse;
import A1BnB.backend.domain.post.dto.PostDto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    void registerPost(String username, PostUploadRequest requestParam);
    void deletePost(Long postId);
    Page<PostResponse> getAllPosts(Pageable pageable);
    Page<PostResponse> searchByCondition(PostSearchRequest requestParam, Pageable pageable);
    PostDetailResponse getPostDetail(Long postId);
    PostLikeCheckResponse checkLike(String username, Long postId);
    void likePost(String username, Long postId);
    void unlikePost(String username, Long postId);
    void bookPost(String username, Long postId, PostBookRequest requestParam);
    void unbookPost(String username, Long postId, Long bookId);
    Page<PostResponse> getLikeRanking(Pageable pageable);
    PostLikeCountResponse getLikeCount(Long postId);
}
