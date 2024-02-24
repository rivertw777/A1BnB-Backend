package A1BnB.backend.domain.member.dto;

import jakarta.validation.constraints.NotNull;


public record MemberLoginRequest(@NotNull String name,@NotNull String password) {
    public MemberLoginRequest {
    }
}