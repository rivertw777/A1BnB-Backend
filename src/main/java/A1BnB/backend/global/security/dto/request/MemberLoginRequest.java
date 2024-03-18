package A1BnB.backend.global.security.dto.request;

import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(@NotNull String name,@NotNull String password) {
    public MemberLoginRequest {
    }
}