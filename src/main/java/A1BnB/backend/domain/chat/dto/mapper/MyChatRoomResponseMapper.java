package A1BnB.backend.domain.chat.dto.mapper;

import A1BnB.backend.domain.chat.dto.response.MyChatRoomResponse;
import A1BnB.backend.domain.chat.model.ChatMessage;
import A1BnB.backend.domain.chat.model.ChatRoom;
import A1BnB.backend.domain.chat.repository.ChatMessageRepository;
import A1BnB.backend.domain.member.model.entity.Member;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyChatRoomResponseMapper {

    private final ChatMessageRepository chatMessageRepository;

    public List<MyChatRoomResponse> toRoomResponses(List<ChatRoom> chatRooms, Member sender) {
        return chatRooms.stream()
                .map(post -> toRoomResponse(post, sender))
                .collect(Collectors.toList());
    }

    public MyChatRoomResponse toRoomResponse(ChatRoom chatRoom, Member sender) {
        Optional<ChatMessage> lastChatMessage = chatMessageRepository.findFirstByChatRoomOrderByCreatedAtDesc(chatRoom);
        String lastMessage = lastChatMessage.map(ChatMessage::getMessage).orElse("");
        LocalDateTime createdAt = lastChatMessage.map(ChatMessage::getCreatedAt).orElse(null);
        return new MyChatRoomResponse(
                findReceiverName(chatRoom, sender),
                lastMessage,
                createdAt
        );
    }

    private String findReceiverName(ChatRoom chatRoom, Member sender) {
        Set<Member> participants = chatRoom.getParticipants();
        Optional<Member> receiver = participants.stream()
                .filter(participant -> !participant.equals(sender))
                .findFirst();
        return receiver.get().getName();
    }

}
