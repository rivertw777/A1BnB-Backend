package A1BnB.backend.domain.post.controller;

import A1BnB.backend.domain.post.dto.PostSearchRequest;
import A1BnB.backend.domain.post.dto.PostUploadRequest;
import A1BnB.backend.domain.post.dto.PostResponse;
import A1BnB.backend.domain.post.service.PostService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private final PostService postService;

    // 게시물 업로드
    @PostMapping(value = "")
    public ResponseEntity<Void> uploadPost(@AuthenticationPrincipal(expression = "username") String username,
                                           @Valid @ModelAttribute PostUploadRequest uploadParam) throws IOException {
        // 게시물 등록
        postService.registerPost(username, uploadParam);
        return ResponseEntity.ok().build();
    }

    // 게시물 전체 조회
    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        // 게시물 응답 DTO 반환
        List<PostResponse> postResponses = postService.getAllPosts();
        return ResponseEntity.ok(postResponses);
    }

    // 게시물 검색
    @PostMapping("/search")
    public ResponseEntity<List<PostResponse>> searchByCondition(
            @Valid @ModelAttribute PostSearchRequest searchCondition) {
        List<PostResponse> postResponses = postService.searchByCondition(searchCondition);
        return ResponseEntity.ok(postResponses);
    }

}
