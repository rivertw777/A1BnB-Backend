package A1BnB.backend.domain.member.service;

import static A1BnB.backend.global.exception.constants.MemberExceptionMessages.DUPLICATE_NAME;
import static A1BnB.backend.global.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.domain.date.service.DateService;
import A1BnB.backend.domain.member.dto.mapper.GuestReservationResponseMapper;
import A1BnB.backend.domain.member.dto.mapper.HostPostResponseMapper;
import A1BnB.backend.domain.member.dto.mapper.HostReservationResponseMapper;
import A1BnB.backend.domain.member.dto.mapper.MyLikePostResponseMapper;
import A1BnB.backend.domain.member.dto.request.CkeckSameMemberRequest;
import A1BnB.backend.domain.member.dto.response.CheckSameMemberResponse;
import A1BnB.backend.domain.member.dto.response.HostPostResponse;
import A1BnB.backend.domain.member.dto.response.HostReservationResponse;
import A1BnB.backend.domain.member.dto.response.MyLikePostResponse;
import A1BnB.backend.domain.member.dto.response.NearestCheckInDateResponse;
import A1BnB.backend.domain.member.dto.response.GuestReservationResponse;
import A1BnB.backend.domain.member.dto.response.SettleAmountResponse;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.member.dto.request.MemberSignupRequest;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.domain.postBook.model.PostBookInfo;
import A1BnB.backend.domain.postBook.service.PostBookService;
import A1BnB.backend.domain.postLike.model.entity.PostLikeInfo;
import A1BnB.backend.domain.postLike.service.PostLikeService;
import A1BnB.backend.global.exception.MemberException;
import A1BnB.backend.domain.member.model.Role;
import A1BnB.backend.domain.member.repository.MemberRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final PostBookService postBookService;
    private final PostLikeService postLikeService;
    private final DateService dateService;

    private final GuestReservationResponseMapper guestReservationResponseMapper;
    private final HostReservationResponseMapper hostReservationResponseMapper;
    private final HostPostResponseMapper hostPostResponseMapper;
    private final MyLikePostResponseMapper myLikePostResponseMapper;

    // 회원 가입
    @Override
    @Transactional
    public void registerUser(MemberSignupRequest requestParam) {
        validateDuplicateName(requestParam.name());
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(requestParam.password());
        saveMember(requestParam.name(), encodedPassword);
    }

    // Member 엔티티 저장
    private void saveMember(String username, String encodedPassword) {
        Member member = Member.builder()
                .name(username)
                .password(encodedPassword)
                .roles(Collections.singletonList(Role.HOST))
                .build();
        memberRepository.save(member);
    }

    // 이름의 중복 검증
    private void validateDuplicateName(String username){
        Optional<Member> findMember = memberRepository.findByName(username);
        if (findMember.isPresent()) {
            throw new MemberException(DUPLICATE_NAME.getMessage());
        }
    }

    // 이름으로 찾아서 반환
    @Override
    @Transactional(readOnly = true)
    public Member findMember(String username){
        return memberRepository.findByName(username)
                .orElseThrow(()->new MemberException(MEMBER_NAME_NOT_FOUND.getMessage()));
    }

    // 내 정산 금액 조회 (호스트)
    @Override
    @Transactional(readOnly = true)
    public SettleAmountResponse findSettlementAmount(String username) {
        Member currentMember = findMember(username);
        return new SettleAmountResponse(currentMember.getSettlementAmount());
    }

    // 내 게시물 조회 (호스트)
    @Override
    @Transactional(readOnly = true)
    public List<HostPostResponse> findHostPosts(String username) {
        Member currentMember = findMember(username);
        List<Post> posts = currentMember.getPosts();
        return hostPostResponseMapper.toPostResponses(posts);
    }

    // 예약 내역 조회 (호스트)
    @Override
    @Transactional(readOnly = true)
    public List<HostReservationResponse> findHostReservations(String username) {
        Member currentMember = findMember(username);
        List<Post> posts = currentMember.getPosts();
        List<PostBookInfo> postBookInfos = postBookService.findByPosts(posts);
        return hostReservationResponseMapper.toReservationResponses(postBookInfos);
    }

    // 가장 가까운 체크인 예정 날짜 조회 (게스트)
    @Override
    @Transactional(readOnly = true)
    public NearestCheckInDateResponse findNearestCheckInDate(String username) {
        Member currentMember = findMember(username);
        List<PostBookInfo> postBookInfos = postBookService.findByGuest(currentMember);
        LocalDate nearestCheckInDate = dateService.getNearestCheckInDate(postBookInfos);
        return new NearestCheckInDateResponse(nearestCheckInDate);
    }

    // 예약 내역 조회 (게스트)
    @Override
    @Transactional(readOnly = true)
    public List<GuestReservationResponse> findGuestReservations(String username) {
        Member currentMember = findMember(username);
        List<PostBookInfo> postBookInfos = postBookService.findByGuest(currentMember);
        return guestReservationResponseMapper.toReservationResponses(postBookInfos);
    }

    // 좋아요 게시물 조회 (게스트)
    @Override
    @Transactional(readOnly = true)
    public List<MyLikePostResponse> findLikePosts(String username) {
        Member currentMember = findMember(username);
        List<PostLikeInfo> postLikeInfos = postLikeService.findByMember(currentMember);
        List<Post> posts = postLikeInfos.stream()
                .map(postLikeInfo -> postLikeInfo.getPost())
                .collect(Collectors.toList());
        return myLikePostResponseMapper.toPostResponses(posts);
    }

    // 동일 인물인지 판별
    @Override
    public CheckSameMemberResponse checkSameMember(String username, CkeckSameMemberRequest requestParam) {
        boolean isSameMember = username.equals(requestParam.memberName());
        return new CheckSameMemberResponse(isSameMember);
    }

    @Override
    public void deleteAllMember() {
        memberRepository.deleteAll();
    }

}
