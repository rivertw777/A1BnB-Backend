package A1BnB.backend.domain.member.dto;

import java.time.LocalDateTime;

public record ChatMessageInfo(String senderName, String message, LocalDateTime createdAt) {
    public ChatMessageInfo{
    }
}
