package A1BnB.backend.domain.chat.service;

import A1BnB.backend.domain.chat.dto.request.ChatRequest;
import A1BnB.backend.domain.chat.dto.request.FindChatRoomRequest;
import A1BnB.backend.domain.chat.dto.response.ChatRoomResponse;
import A1BnB.backend.domain.chat.model.ChatRoom;
import A1BnB.backend.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    public void saveMessage(ChatRequest requestParam) {
        System.out.println(requestParam.message());
    }

    @Transactional
    public ChatRoomResponse findRoom(String username, FindChatRoomRequest requestParam) {
        ChatRoom chatRoom = chatRoomRepository.findByHostAndGuest(requestParam.receiver(), username)
                .orElseGet(() -> saveChatRoom(requestParam.receiver(), username));
        return new ChatRoomResponse(chatRoom.getId());
    }

    // sender: guest, receiver: host
    public ChatRoom saveChatRoom(String host, String guest) {
        ChatRoom chatRoom = ChatRoom.builder()
                .host(host)
                .guest(guest)
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

}
