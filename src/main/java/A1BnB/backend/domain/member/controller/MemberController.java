package A1BnB.backend.domain.member.controller;

import A1BnB.backend.domain.member.dto.request.MemberSignupRequest;
import A1BnB.backend.domain.member.dto.response.HostPostResponse;
import A1BnB.backend.domain.member.dto.response.HostReservationResponse;
import A1BnB.backend.domain.member.dto.response.NearestCheckInDateResponse;
import A1BnB.backend.domain.member.dto.response.GuestReservationResponse;
import A1BnB.backend.domain.member.dto.response.SettleAmountResponse;
import A1BnB.backend.domain.member.service.MemberService;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("")
    public void signUp(@Valid @RequestBody MemberSignupRequest requestParam) {
        memberService.registerUser(requestParam);
    }

    // 내 정산 금액 조회 (호스트)
    @GetMapping("hosts/amount")
    public SettleAmountResponse findSettlementAmount(@AuthenticationPrincipal(expression = "username") String username) {
        return memberService.findSettlementAmount(username);
    }

    // 내 게시물 조회 (호스트)
    @GetMapping("hosts/posts")
    public List<HostPostResponse> findHostPosts(@AuthenticationPrincipal(expression = "username") String username) {
        return memberService.findHostPosts(username);
    }

    // 예약 내역 조회 (호스트)
    @GetMapping("hosts/reservations")
    public List<HostReservationResponse> findHostReservations(
            @AuthenticationPrincipal(expression = "username") String username) {
        return memberService.findHostReservations(username);
    }

    // 가장 가까운 체크인 예정 날짜 조회 (게스트)
    @GetMapping("guests/checkin")
    public NearestCheckInDateResponse findNearestCheckInDate(@AuthenticationPrincipal(expression = "username") String username) {
        return memberService.findNearestCheckInDate(username);
    }

    // 예약 내역 조회 (게스트)
    @GetMapping("guests/reservations")
    public List<GuestReservationResponse> findGuestReservations(
            @AuthenticationPrincipal(expression = "username") String username) {
        return memberService.findGuestReservations(username);
    }

    // 좋아요 게시물 조회 (게스트)
    @GetMapping("guests/posts")
    public List<PostResponse> findLikePosts(@AuthenticationPrincipal(expression = "username") String username) {
        return memberService.findLikePosts(username);
    }

}
