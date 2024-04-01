package A1BnB.backend.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;

public record FindChatRoomRequest(@NotNull String receiverName) {
    public FindChatRoomRequest {
    }
}
