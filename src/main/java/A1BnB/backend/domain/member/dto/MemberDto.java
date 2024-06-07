package A1BnB.backend.domain.member.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDto {

    // 회원 가입 요청
    public record MemberSignupRequest(@NotNull String name, @NotNull String password) {
    }

    // 동일 인물 확인 요청
    public record CkeckSameMemberRequest(@NotNull String memberName) {
    }

    // 채팅방 조회 요청
    public record FindChatRoomRequest(@NotNull String receiverName) {
    }

    // 채팅방 조회 응답
    public record ChatRoomResponse(Long roomId, List<ChatMessageInfo> messageInfoList) {
    }

    // 게스트 예약 정보 응답
    public record GuestReservationResponse(Long bookId, Long postId, LocalDateTime checkInDate,
                                           LocalDateTime checkOutDate,
                                           String thumbnail, String location, String hostName, Integer paymentAmount) {
    }

    // 호스트 예약 정보 응답
    public record HostReservationResponse(Long postId, LocalDateTime checkInDate, LocalDateTime checkOutDate,
                                          String thumbnail, String location, String guestName, Integer paymentAmount) {
    }

    // 호스트 게시물 응답
    public record HostPostResponse(Long postId, LocalDateTime createdAt, String hostName, String thumbnail,
                                   String location, Integer pricePerNight, boolean hasReservation) {
    }

    // 동일 인물 확인 응답
    public record CheckSameMemberResponse(boolean isSameMember) {
    }

    // 정산금 응답
    public record SettleAmountResponse(Integer settleAmount) {
    }

    // 내 채팅방 조회 응답
    public record MyChatRoomResponse(String receiverName, String lastMessage, LocalDateTime createdAt) {
    }

    // 좋아요 게시물 응답
    public record MyLikePostResponse(Long postId, String hostName, String thumbnail,
                                     String location, Integer pricePerNight) {
    }

    // 가장 가까운 체크인 날짜 조회 응답
    public record NearestCheckInDateResponse(LocalDate checkInDate) {
    }

    // 채팅 메시지 정보
    public record ChatMessageInfo(String senderName, String message, LocalDateTime createdAt) {
    }

}
