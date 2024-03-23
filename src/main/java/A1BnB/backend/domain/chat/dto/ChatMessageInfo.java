package A1BnB.backend.domain.chat.dto;

import java.time.LocalDateTime;

public record ChatMessageInfo(String senderName, String message, LocalDateTime createdAt) {
    public ChatMessageInfo{
    }
}
