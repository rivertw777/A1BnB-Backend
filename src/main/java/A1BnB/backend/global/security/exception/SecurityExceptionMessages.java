package A1BnB.backend.global.security.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessages {

    LOGIN_FAILED("로그인에 실패하였습니다. 올바른 정보를 입력 해주세요."),
    UNAUTHORIZED("인증이 필요합니다. 로그인해주세요."),
    NO_AUTHORITY("접근 권한이 없습니다."),
    MALFORMED_SIGNATURE("잘못된 JWT 서명입니다."),
    UNSUPPORTED_TOKEN("지원되지 않는 JWT 토큰입니다."),
    UNMATCHED_SIGNATURE("서명이 일치하지 않습니다."),
    EXPIRED_ACCESS_TOKEN("access 토큰이 만료되었습니다. 다시 발급 받으세요."),
    EXPIRED_REFRESH_TOKEN("refresh 토큰이 만료되었습니다. 다시 로그인 해주세요.");

    private final String message;

}