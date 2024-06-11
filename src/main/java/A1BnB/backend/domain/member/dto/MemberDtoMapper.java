package A1BnB.backend.domain.member.dto;

import A1BnB.backend.domain.chat.model.ChatMessage;
import A1BnB.backend.domain.chat.model.ChatRoom;
import A1BnB.backend.domain.chat.repository.ChatMessageRepository;
import A1BnB.backend.domain.member.dto.MemberDto.ChatMessageInfo;
import A1BnB.backend.domain.member.dto.MemberDto.GuestReservationResponse;
import A1BnB.backend.domain.member.dto.MemberDto.HostPostResponse;
import A1BnB.backend.domain.member.dto.MemberDto.HostReservationResponse;
import A1BnB.backend.domain.member.dto.MemberDto.MyChatRoomResponse;
import A1BnB.backend.domain.member.dto.MemberDto.MyLikePostResponse;
import A1BnB.backend.domain.member.model.Member;
import A1BnB.backend.domain.post.model.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberDtoMapper {

    private final ChatMessageRepository chatMessageRepository;

    // 채팅 메시지 정보 DTO 변환
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

    // 게스트 예약 정보 DTO 변환
    public List<GuestReservationResponse> toGuestReservationResponses(List<PostBookInfo> postBookInfos) {
        return postBookInfos.stream()
                .sorted(Comparator.comparing(PostBookInfo::getId).reversed()) // 역순 정렬
                .map(this::toGuestReservationResponse) // 메소드 참조로 변경
                .collect(Collectors.toList());
    }
    private GuestReservationResponse toGuestReservationResponse(PostBookInfo postBookInfo) {
        return new GuestReservationResponse(
                postBookInfo.getId(),
                postBookInfo.getPost().getId(),
                postBookInfo.getCheckInDate(),
                postBookInfo.getCheckOutDate(),
                postBookInfo.getPost().getPhotos().get(0).getOriginalUrl(),
                postBookInfo.getPost().getLocation(),
                postBookInfo.getPost().getHost().getName(),
                postBookInfo.getPaymentAmount()
        );
    }

    // 호스트 예약 정보 DTO 변환
    public List<HostReservationResponse> toHostReservationResponses(List<PostBookInfo> postBookInfos) {
        return postBookInfos.stream()
                .sorted(Comparator.comparing(PostBookInfo::getId).reversed()) // 역순 정렬
                .map(this::toHostReservationResponse)
                .collect(Collectors.toList());
    }
    private HostReservationResponse toHostReservationResponse(PostBookInfo postBookInfo) {
        return new HostReservationResponse(
                postBookInfo.getPost().getId(),
                postBookInfo.getCheckInDate(),
                postBookInfo.getCheckOutDate(),
                postBookInfo.getPost().getPhotos().get(0).getOriginalUrl(),
                postBookInfo.getPost().getLocation(),
                postBookInfo.getGuest().getName(),
                postBookInfo.getPaymentAmount()
        );
    }

    // 호스트 등록 게시물 응답 DTO 변환
    public List<HostPostResponse> toHostPostResponses(List<Post> posts) {
        return posts.stream()
                .map(post -> toHostPostResponse(post))
                .collect(Collectors.toList());
    }
    private HostPostResponse toHostPostResponse(Post post) {
        return new HostPostResponse(
                post.getId(),
                post.getCreatedAt(),
                post.getHost().getName(),
                post.getPhotos().get(0).getOriginalUrl(),
                post.getLocation(),
                post.getPricePerNight(),
                post.getPostBookInfos().size() > 0
        );
    }

    // 좋아요 게시물 응답 DTO 변환
    public List<MyLikePostResponse> toLikePostResponses(List<Post> posts) {
        return posts.stream()
                .map(post -> toLikePostResponse(post))
                .collect(Collectors.toList());
    }
    public MyLikePostResponse toLikePostResponse(Post post) {
        return new MyLikePostResponse(
                post.getId(),
                post.getHost().getName(),
                post.getPhotos().get(0).getOriginalUrl(),
                post.getLocation(),
                post.getPricePerNight()
        );
    }

    // 내 채팅방 조회 응답 DTO 변환
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
