package A1BnB.backend.domain.member.service;

import static A1BnB.backend.global.exception.constants.MemberExceptionMessages.DUPLICATE_NAME;
import static A1BnB.backend.global.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.domain.member.dto.response.MyPostReservationResponse;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.member.dto.request.MemberSignupRequest;
import A1BnB.backend.domain.post.dto.mapper.PostResponseMapper;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import A1BnB.backend.domain.post.model.entity.Post;
import A1BnB.backend.global.exception.MemberException;
import A1BnB.backend.domain.member.model.Role;
import A1BnB.backend.domain.member.repository.MemberRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final PostResponseMapper postResponseMapper;


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
    @Transactional(readOnly = true)
    public Member findMember(String username){
        return memberRepository.findByName(username)
                .orElseThrow(()->new MemberException(MEMBER_NAME_NOT_FOUND.getMessage()));
    }

    @Override
    @Transactional
    public List<PostResponse> findMyPosts(String username) {
        Member currentMember = findMember(username);
        List<Post> posts = currentMember.getPosts();
        return postResponseMapper.toPostResponses(posts);
    }

    @Override
    @Transactional
    public List<MyPostReservationResponse> findPostReservations(String username) {
        return null;
    }

}
