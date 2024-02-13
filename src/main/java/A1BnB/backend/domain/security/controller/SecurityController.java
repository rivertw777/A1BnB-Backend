package A1BnB.backend.domain.security.controller;

import static A1BnB.backend.domain.security.constants.JwtProperties.TOKEN_PREFIX;

import A1BnB.backend.domain.security.dto.response.AccessTokenResponse;
import A1BnB.backend.domain.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/sercurity")
@RestController
public class SecurityController {

    @Autowired
    private final SecurityService securityService;

    // access 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestHeader("Authorization") String token) {
        String accessToken = token.replace(TOKEN_PREFIX.getValue(), "");
        AccessTokenResponse tokenResponse = securityService.refreshAccessToken(accessToken);
        return ResponseEntity.ok(tokenResponse);
    }

    // 회원 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal(expression = "username") String username){
        securityService.logout(username);
        return ResponseEntity.ok().build();
    }

}
