package A1BnB.backend.domain.chat.dto;

import jakarta.validation.constraints.NotNull;

public record ChatRequest(@NotNull Long roomId, @NotNull String message, @NotNull String receiverName) {
    public ChatRequest {
    }
}