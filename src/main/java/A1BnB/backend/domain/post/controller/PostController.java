package A1BnB.backend.domain.post.controller;


import A1BnB.backend.domain.post.dto.request.PostUploadRequest;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import A1BnB.backend.domain.post.service.PostService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    private final PostService postService;
    @Autowired
    private final WebClient webClient;

    // 게시물 업로드
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> uploadPost(@AuthenticationPrincipal(expression = "username") String username,
                                           @Valid @ModelAttribute PostUploadRequest uploadParam) throws IOException {

        List<MultipartFile> photos = uploadParam.photos();

        String url = "https://dx92uky2ae.execute-api.ap-northeast-2.amazonaws.com/test";

        WebClient.ResponseSpec response = webClient.method(HttpMethod.GET)
                .uri(url)
                .retrieve();
        String responseBody = response.bodyToMono(String.class).block();
        System.out.println(responseBody);

        // 게시물 등록
        //postService.registerPost(username, uploadParam);

        return ResponseEntity.ok().build();
    }

    // 게시물 전체 조회
    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        // 게시물 응답 DTO 반환
        List<PostResponse> postResponses = postService.getAllPosts();
        return ResponseEntity.ok(postResponses);
    }
}
