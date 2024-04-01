package A1BnB.backend.domain.member.dto.response;

import A1BnB.backend.domain.member.dto.ChatMessageInfo;
import java.util.List;

public record ChatRoomResponse(Long roomId, List<ChatMessageInfo> messageInfoList) {
    public ChatRoomResponse {
    }
}
