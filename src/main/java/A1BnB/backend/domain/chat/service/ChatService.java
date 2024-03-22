package A1BnB.backend.domain.chat.service;

import static A1BnB.backend.global.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.domain.chat.dto.mapper.MyChatRoomResponseMapper;
import A1BnB.backend.domain.chat.dto.request.ChatRequest;
import A1BnB.backend.domain.chat.dto.request.FindChatRoomRequest;
import A1BnB.backend.domain.chat.dto.response.ChatRoomResponse;
import A1BnB.backend.domain.chat.dto.response.MyChatRoomResponse;
import A1BnB.backend.domain.chat.model.ChatMessage;
import A1BnB.backend.domain.chat.model.ChatRoom;
import A1BnB.backend.domain.chat.repository.ChatMessageRepository;
import A1BnB.backend.domain.chat.repository.ChatRoomRepository;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.member.service.MemberService;
import A1BnB.backend.global.exception.MemberException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final MemberService memberService;

    private final MyChatRoomResponseMapper myChatRoomResponseMapper;

    @Transactional
    public void saveMessage(String username, ChatRequest requestParam) {
        saveChatMessage(username, requestParam);
    }

    private void saveChatMessage(String username, ChatRequest requestParam) {
        ChatRoom chatRoom = findChatRoom(requestParam.roomId());
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(username)
                .message(requestParam.message())
                .build();
        chatMessageRepository.save(chatMessage);
    }

    private ChatRoom findChatRoom(Long roomId){
        return chatRoomRepository.findById(roomId)
                .orElseThrow(()->new IllegalArgumentException());
    }

    // 수신자 송신자로 방 조회, 없다면 새로 생성
    @Transactional
    public ChatRoomResponse findRoom(String username, FindChatRoomRequest requestParam) {
        Member receiver = memberService.findMember(requestParam.receiverName());
        Member sender = memberService.findMember(username);

        ChatRoom chatRoom = chatRoomRepository.findChatRoomByParticipants(receiver, sender)
                .orElseGet(() -> saveChatRoom(sender, receiver));
        return new ChatRoomResponse(chatRoom.getId());
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

    @Transactional
    public List<MyChatRoomResponse> findMyChatRooms(String username) {
        Member currentMember = memberService.findMember(username);
        List<ChatRoom> chatRooms = chatRoomRepository.findByParticipants(currentMember);
        return myChatRoomResponseMapper.toRoomResponses(chatRooms, currentMember);
    }

}
