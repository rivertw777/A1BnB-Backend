package A1BnB.backend.domain.member.service;

import A1BnB.backend.domain.member.dto.MemberDto.ChatRoomResponse;
import A1BnB.backend.domain.member.dto.MemberDto.CheckSameMemberResponse;
import A1BnB.backend.domain.member.dto.MemberDto.CkeckSameMemberRequest;
import A1BnB.backend.domain.member.dto.MemberDto.GuestReservationResponse;
import A1BnB.backend.domain.member.dto.MemberDto.HostPostResponse;
import A1BnB.backend.domain.member.dto.MemberDto.HostReservationResponse;
import A1BnB.backend.domain.member.dto.MemberDto.MemberSignupRequest;
import A1BnB.backend.domain.member.dto.MemberDto.MyChatRoomResponse;
import A1BnB.backend.domain.member.dto.MemberDto.MyLikePostResponse;
import A1BnB.backend.domain.member.dto.MemberDto.NearestCheckInDateResponse;
import A1BnB.backend.domain.member.dto.MemberDto.SettleAmountResponse;
import A1BnB.backend.domain.member.dto.MemberDto.FindChatRoomRequest;
import A1BnB.backend.domain.member.model.Member;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void registerUser(MemberSignupRequest requestParam);

    Member findMember(String name);

    SettleAmountResponse findSettlementAmount(String username);

    List<HostPostResponse> findHostPosts(String username);

    List<HostReservationResponse> findHostReservations(String username);

    NearestCheckInDateResponse findNearestCheckInDate(String username);

    List<GuestReservationResponse> findGuestReservations(String username);

    List<MyLikePostResponse> findLikePosts(String username);

    CheckSameMemberResponse checkSameMember(String username, CkeckSameMemberRequest requestParam);

    ChatRoomResponse findRoom(String username, FindChatRoomRequest requestParam);

    List<MyChatRoomResponse> findMyChatRooms(String username);

    void deleteAllMember();
}