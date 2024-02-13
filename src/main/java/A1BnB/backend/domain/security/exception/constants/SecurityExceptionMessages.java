package A1BnB.backend.domain.security.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessages {

    LOGIN_FAILED("인증에 실패하였습니다. 다시 로그인 해주세요."),
    NO_AUTHORITY("접근 권한이 없습니다."),
    UNAUTHENTICATED("권한이 없습니다. 로그인 해주세요."),
    EXPIRED_ACCESS_TOKEN("access 토큰이 만료되었습니다. 다시 발급받으세요."),
    EXPIRED_REFRESH_TOKEN("refresh 토큰이 만료되었습니다. 다시 로그인 해주세요.");

    private final String message;
}