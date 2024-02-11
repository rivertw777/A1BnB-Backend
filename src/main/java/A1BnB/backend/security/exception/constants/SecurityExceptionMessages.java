package A1BnB.backend.security.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityExceptionMessages {

    LOGIN_FAILED("로그인이 실패하였습니다."),
    NO_AUTHORITY("권한이 없습니다."),
    UNAUTHENTICATED("인증되지 않은 사용자입니다. 로그인해주세요.");

    private final String message;
}