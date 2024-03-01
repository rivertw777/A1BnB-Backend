package A1BnB.backend.global.security.controller;

import A1BnB.backend.global.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/security")
@RestController
public class SecurityController {

    private final SecurityService securityService;

    // 회원 로그아웃
    @PostMapping("/logout")
    public void logout(@AuthenticationPrincipal(expression = "username") String username){
        securityService.logout(username);
    }

}
