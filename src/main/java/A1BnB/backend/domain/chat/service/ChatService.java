package A1BnB.backend.domain.chat.service;

import A1BnB.backend.domain.chat.dto.request.ChatRequest;
import A1BnB.backend.domain.chat.dto.request.FindChatRoomRequest;
import A1BnB.backend.domain.chat.dto.response.ChatRoomResponse;
import A1BnB.backend.domain.chat.model.ChatRoom;
import A1BnB.backend.domain.chat.repository.ChatRoomRepository;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;

    private final MemberService memberService;

    public void saveMessage(ChatRequest requestParam) {

        System.out.println(requestParam.message());
    }

    // 수신자 송신자로 방 조회, 업다면 새로 생성
    @Transactional
    public ChatRoomResponse findRoom(String username, FindChatRoomRequest requestParam) {
        Member receiver = memberService.findMember(requestParam.receiver());
        Member sender = memberService.findMember(username);

        ChatRoom chatRoom = chatRoomRepository.findChatRoomByParticipants(receiver, sender)
                .orElseGet(() -> saveChatRoom(sender, receiver));
        return new ChatRoomResponse(chatRoom.getId());
    }

    // 방 생성 시, sender: 나, receiver: 상대
    public ChatRoom saveChatRoom(Member sender, Member receiver) {
        ChatRoom chatRoom = ChatRoom.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
        chatRoomRepository.save(chatRoom);
        return chatRoom;
    }

}
