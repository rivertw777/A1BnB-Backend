package A1BnB.backend.domain.post.controller;

import A1BnB.backend.domain.post.dto.request.PostBookRequest;
import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
import A1BnB.backend.domain.post.dto.response.PostLikeCheckResponse;
import A1BnB.backend.domain.post.dto.response.PostLikeCountResponse;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import A1BnB.backend.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;

    // 게시물 업로드
    @PostMapping
    public void uploadPost(@AuthenticationPrincipal(expression = "username") String username,
                           @Valid @ModelAttribute PostUploadRequest requestParam) {
        postService.registerPost(username, requestParam);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public void deletePost(@Valid @PathVariable("postId") Long postId){
        postService.deletePost(postId);
    }

    // 게시물 전체 조회
    @GetMapping
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }


    // 게시물 상세 조회
    @GetMapping("/{postId}")
    public PostDetailResponse getPostDetail(@Valid @PathVariable("postId") Long postId) {
        return postService.getPostDetail(postId);
    }

    // 회원 게시물 좋아요 여부 확인
    @GetMapping("/{postId}/like/check")
    public PostLikeCheckResponse checkLike(@AuthenticationPrincipal(expression = "username") String username,
                                           @Valid @PathVariable("postId") Long postId) {
        return postService.checkLike(username, postId);
    }

    // 게시물 검색
    @PostMapping("/search")
    public Page<PostResponse> searchByCondition(@Valid @RequestBody PostSearchRequest requestParam,
                                                Pageable pageable) {
        return postService.searchByCondition(requestParam, pageable);
    }

    // 게시물 좋아요
    @PostMapping("/{postId}/like")
    public void likePost(@AuthenticationPrincipal(expression = "username") String username,
                         @Valid @PathVariable("postId") Long postId) {
        postService.likePost(username, postId);
    }

    // 게시물 좋아요 취소
    @DeleteMapping("/{postId}/like")
    public void unlikePost(@AuthenticationPrincipal(expression = "username") String username,
                         @Valid @PathVariable("postId") Long postId) {
        postService.unlikePost(username, postId);
    }

    // 게시물 좋아요 수
    @GetMapping("/{postId}/like/count")
    public PostLikeCountResponse getLikeCount(@Valid @PathVariable("postId") Long postId) {
        return postService.getLikeCount(postId);
    }

    // 숙소 예약
    @PostMapping("/{postId}/book")
    public void bookPost(@AuthenticationPrincipal(expression = "username") String username,
                         @Valid @PathVariable("postId") Long postId,
                         @Valid @ModelAttribute PostBookRequest requestParam) {
        postService.bookPost(username, postId, requestParam);
    }

    // 게시물 예약 취소
    @DeleteMapping("/{postId}/book/{bookId}")
    public void unbookPost(@AuthenticationPrincipal(expression = "username") String username,
                          @Valid @PathVariable("postId") Long postId, @Valid @PathVariable("bookId") Long bookId) {
        postService.unbookPost(username, postId, bookId);
    }

    // 게시물 인기순 조회
    @GetMapping("like")
    public Page<PostResponse> getlikeRanking(Pageable pageable) {
        return postService.getLikeRanking(pageable);
    }

}
