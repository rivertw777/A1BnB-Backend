package A1BnB.backend.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberSignupRequest(@NotNull String name, @NotNull String password) {
    public MemberSignupRequest {
    }
}
