package A1BnB.backend.domain.member.dto.mapper;

import A1BnB.backend.domain.member.dto.ChatMessageInfo;
import A1BnB.backend.domain.chat.model.ChatMessage;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageInfoMapper {

    public List<ChatMessageInfo> toMessageInfoList(List<ChatMessage> chatMessages) {
        return chatMessages.stream()
                .sorted(Comparator.comparing(ChatMessage::getCreatedAt)) // createdAt 기준으로 오름차순 정렬
                .map(this::toMessageInfo)
                .collect(Collectors.toList());
    }

    public ChatMessageInfo toMessageInfo(ChatMessage chatMessage) {
        return new ChatMessageInfo(
                chatMessage.getSenderName(),
                chatMessage.getMessage(),
                chatMessage.getCreatedAt()
        );
    }

}
