package A1BnB.backend.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "회원 가입 요청 DTO")
public record MemberSignupRequest(@NotNull String name, @NotNull String password) {
    public MemberSignupRequest {
    }
}
