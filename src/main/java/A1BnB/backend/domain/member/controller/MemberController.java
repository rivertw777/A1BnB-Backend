package A1BnB.backend.domain.member.controller;

import A1BnB.backend.domain.member.dto.request.MemberSignupRequest;
import A1BnB.backend.domain.member.dto.response.MyPostReservationResponse;
import A1BnB.backend.domain.member.service.MemberService;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public void signUp(@Valid @RequestBody MemberSignupRequest requestParam) {
        // 회원 가입
        memberService.registerUser(requestParam);
    }

    @GetMapping("/posts")
    public List<PostResponse> findMyPosts(@AuthenticationPrincipal(expression = "username") String username) {
        return memberService.findMyPosts(username);
    }

    @GetMapping("/posts/reservations")
    public List<MyPostReservationResponse> findPostReservations(
            @AuthenticationPrincipal(expression = "username") String username) {
        return memberService.findPostReservations(username);
    }


}
