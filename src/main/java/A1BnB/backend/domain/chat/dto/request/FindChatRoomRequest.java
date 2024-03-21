package A1BnB.backend.domain.chat.dto.request;

import jakarta.validation.constraints.NotNull;

public record FindChatRoomRequest(@NotNull String receiver) {
    public FindChatRoomRequest {
    }
}
