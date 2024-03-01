package A1BnB.backend.domain.post.controller;

import A1BnB.backend.domain.post.dto.PostDetailResponse;
import A1BnB.backend.domain.post.dto.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostUploadRequest;
import A1BnB.backend.domain.post.dto.PostResponse;
import A1BnB.backend.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                           @Valid @ModelAttribute PostUploadRequest uploadParam) {
        postService.registerPost(username, uploadParam);
    }

    // 게시물 전체 조회
    @GetMapping
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }

    // 게시물 단일 조회
    @GetMapping("/{postId}")
    public PostDetailResponse getPostDetail(@Valid @PathVariable("postId") Long postId){
        return postService.getPostDetail(postId);
    }

    // 게시물 검색
    @PostMapping("/search")
    public Page<PostResponse> searchByCondition(@Valid @RequestBody PostSearchRequest searchCondition,
                                                Pageable pageable) {
        return postService.searchByCondition(searchCondition, pageable);
    }

}
