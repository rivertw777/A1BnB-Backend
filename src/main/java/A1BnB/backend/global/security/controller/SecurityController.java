package A1BnB.backend.global.security.controller;

import A1BnB.backend.global.security.dto.AccessTokenResponse;
import A1BnB.backend.global.security.service.SecurityService;
import A1BnB.backend.global.security.constants.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/security")
@RestController
public class SecurityController {

    private final SecurityService securityService;

    // access 토큰 재발급
    @PostMapping("/refresh")
    public AccessTokenResponse refreshToken(@RequestHeader("Authorization") String token) {
        String accessToken = token.replace(JwtProperties.TOKEN_PREFIX.getValue(), "");
        return securityService.refreshAccessToken(accessToken);
    }

    // 회원 로그아웃
    @GetMapping("/logout")
    public void logout(@AuthenticationPrincipal(expression = "username") String username){
        securityService.logout(username);
    }

}
