package A1BnB.backend.domain.member.dto.response;

import java.time.LocalDateTime;

public record MyChatRoomResponse(String receiverName, String lastMessage, LocalDateTime createdAt) {
    public MyChatRoomResponse {
    }
}
