package A1BnB.backend.post.controller;


import A1BnB.backend.post.dto.request.PostUploadRequest;
import A1BnB.backend.post.dto.response.PostResponse;
import A1BnB.backend.post.service.PostService;
import A1BnB.backend.security.utils.SecurityUtil;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final SecurityUtil securityUtil;
    @Autowired
    private final PostService postService;

    // 게시물 업로드
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadPost(@Valid @ModelAttribute PostUploadRequest uploadParam) throws IOException {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();
        // 게시물 등록
        postService.registerPost(memberId, uploadParam);
        return ResponseEntity.ok().build();
    }

    // 게시물 전체 조회
    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        // 로그인 중인 회원 id
        Long memberId = securityUtil.getCurrentMemberId();
        // 게시물 응답 DTO 반환
        List<PostResponse> postResponses = postService.getAllPosts(memberId);
        return ResponseEntity.ok(postResponses);
    }
}
