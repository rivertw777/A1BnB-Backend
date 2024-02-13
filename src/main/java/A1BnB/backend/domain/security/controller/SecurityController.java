package A1BnB.backend.domain.security.controller;

import A1BnB.backend.domain.security.dto.response.AccessTokenResponse;
import A1BnB.backend.domain.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api/sercurity")
@Controller
public class SecurityController {

    @Autowired
    private final SecurityService securityService;

    // access 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestHeader("Authorization") String accessToken) {
        AccessTokenResponse tokenResponse = securityService.refreshAccessToken(accessToken);
        return ResponseEntity.ok(tokenResponse);
    }

    // 회원 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String accessToken){
        // 인증된 사용자의 정보 삭제
        SecurityContextHolder.clearContext();
        // redis refresh 토큰 삭제
        securityService.logout(accessToken);
        return ResponseEntity.ok().build();
    }

}
