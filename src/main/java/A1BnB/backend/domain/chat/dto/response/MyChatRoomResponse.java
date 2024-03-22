package A1BnB.backend.domain.chat.dto.response;

import java.time.LocalDateTime;

public record MyChatRoomResponse(String receiverName, String lastMessage, LocalDateTime createdAt) {

}
