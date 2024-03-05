package A1BnB.backend.domain.post.controller;

import A1BnB.backend.domain.post.dto.request.PostBookRequest;
import A1BnB.backend.domain.post.dto.response.PostDetailResponse;
import A1BnB.backend.domain.post.dto.request.PostSearchRequest;
import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
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

    // 게시물 전체 조회
    @GetMapping
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    // 게시물 상세 조회 (인증 X)
    @GetMapping("/{postId}")
    public PostDetailResponse getPostDetail(@Valid @PathVariable("postId") Long postId){
        return postService.getPostDetail(null, postId);
    }

    // 게시물 상세 조회 (인증 O)
    @PostMapping("/{postId}")
    public PostDetailResponse getPostDetailWithAuth(@AuthenticationPrincipal(expression = "username") String username,
                                                    @Valid @PathVariable("postId") Long postId){
        return postService.getPostDetail(username, postId);
    }

    // 게시물 검색
    @PostMapping("/search")
    public Page<PostResponse> searchByCondition(@Valid @RequestBody PostSearchRequest searchCondition,
                                                Pageable pageable) {
        return postService.searchByCondition(searchCondition, pageable);
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

    // 숙소 예약
    @PostMapping("/{postId}/book")
    public void bookPost(@AuthenticationPrincipal(expression = "username") String username,
                         @Valid @PathVariable("postId") Long postId,
                         @Valid @ModelAttribute PostBookRequest requestParam) {
        postService.bookPost(username, postId, requestParam);
    }

    // 게시물 예약 취소
    @DeleteMapping("/{postId}/book")
    public void unbookPost(@AuthenticationPrincipal(expression = "username") String username,
                          @Valid @PathVariable("postId") Long postId) {
        postService.unbookPost(username, postId);
    }

}
