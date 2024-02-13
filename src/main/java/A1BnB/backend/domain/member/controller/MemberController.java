package A1BnB.backend.domain.member.controller;

import A1BnB.backend.domain.member.dto.request.MemberSignupRequest;
import A1BnB.backend.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<Void> signUp(@Valid @RequestBody MemberSignupRequest signupParam) {
        // 회원 가입
        memberService.registerUser(signupParam);
        return ResponseEntity.ok().build();
    }

}