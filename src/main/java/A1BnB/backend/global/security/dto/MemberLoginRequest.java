package A1BnB.backend.global.security.dto;

import jakarta.validation.constraints.NotNull;

public record MemberLoginRequest(@NotNull String name,@NotNull String password) {
    public MemberLoginRequest {
    }
}