package A1BnB.backend.global.exception.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostExceptionMessages {

    POST_NOT_FOUND("해당하는 게시물이 없습니다."),
    POST_BOOK_INFO_NOT_FOUND("예약 정보가 없습니다.");

    private final String message;

}

