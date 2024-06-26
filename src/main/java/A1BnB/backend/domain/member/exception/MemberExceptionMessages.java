package A1BnB.backend.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionMessages {

    DUPLICATE_NAME("이미 존재하는 이름입니다."),
    MEMBER_NAME_NOT_FOUND("해당하는 이름을 가진 회원이 없습니다.");

    private final String message;

}

