package A1BnB.backend.domain.member.service;

import A1BnB.backend.domain.member.dto.request.MemberSignupRequest;
import A1BnB.backend.domain.member.dto.response.HostPostResponse;
import A1BnB.backend.domain.member.dto.response.HostReservationResponse;
import A1BnB.backend.domain.member.dto.response.NearestCheckInDateResponse;
import A1BnB.backend.domain.member.dto.response.GuestReservationResponse;
import A1BnB.backend.domain.member.dto.response.SettleAmountResponse;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void registerUser(MemberSignupRequest signupParam);
    Member findMember(String name);
    SettleAmountResponse findSettlementAmount(String username);
    List<HostPostResponse> findHostPosts(String username);
    List<HostReservationResponse> findHostReservations(String username);
    NearestCheckInDateResponse findNearestCheckInDate(String username);
    List<GuestReservationResponse> findGuestReservations(String username);
    List<PostResponse> findLikePosts(String username);
}