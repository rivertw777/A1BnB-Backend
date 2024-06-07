package A1BnB.backend.domain.chat.service;

import A1BnB.backend.domain.chat.dto.ChatRequest;
import A1BnB.backend.domain.chat.model.ChatMessage;
import A1BnB.backend.domain.chat.model.ChatRoom;
import A1BnB.backend.domain.chat.repository.ChatMessageRepository;
import A1BnB.backend.domain.chat.repository.ChatRoomRepository;
import A1BnB.backend.domain.member.model.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public void saveMessage(String username, ChatRequest requestParam) {
        saveChatMessage(username, requestParam);
    }

    private void saveChatMessage(String username, ChatRequest requestParam) {
        ChatRoom chatRoom = findChatRoom(requestParam.roomId());
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .senderName(username)
                .message(requestParam.message())
                .build();
        chatMessageRepository.save(chatMessage);
    }

    private ChatRoom findChatRoom(Long roomId){
        return chatRoomRepository.findById(roomId)
                .orElseThrow(()->new IllegalArgumentException());
    }

    // 방 생성 시, sender: 나, receiver: 상대
    private ChatRoom saveChatRoom(Member sender, Member receiver) {
        ChatRoom chatRoom = ChatRoom.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

    // 참가자 이름으로 채팅방 조회
    public List<ChatRoom> findByParticipants(Member currentMember) {
        return chatRoomRepository.findByParticipants(currentMember);
    }

    public ChatRoom findChatRoomByParticipants(Member receiver, Member sender) {
        return chatRoomRepository.findChatRoomByParticipants(receiver, sender)
                .orElseGet(() -> saveChatRoom(sender, receiver));
    }

}
