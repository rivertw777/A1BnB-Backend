package A1BnB.backend.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record CkeckSameMemberRequest(@NotNull String memberName) {
    public CkeckSameMemberRequest{
    }
}
