package A1BnB.backend.domain.member.service;

import static A1BnB.backend.domain.member.exception.constants.MemberExceptionMessages.DUPLICATE_NAME;
import static A1BnB.backend.domain.member.exception.constants.MemberExceptionMessages.MEMBER_NAME_NOT_FOUND;

import A1BnB.backend.domain.member.exception.MemberNotFoundException;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.member.dto.MemberSignupRequest;
import A1BnB.backend.domain.member.exception.DuplicateNameException;
import A1BnB.backend.domain.member.model.Role;
import A1BnB.backend.domain.member.repository.MemberRepository;

import java.util.Collections;
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

    // 회원 가입
    @Override
    @Transactional
    public void registerUser(MemberSignupRequest signupParam) {
        validateDuplicateName(signupParam.name());
        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(signupParam.password());
        saveMember(signupParam, encodedPassword);
    }

    // Member 엔티티 저장
    private void saveMember(MemberSignupRequest signupParam, String encodedPassword) {
        Member member = Member.builder()
                .name(signupParam.name())
                .password(encodedPassword)
                .roles(Collections.singletonList(Role.USER))
                .build();
        memberRepository.save(member);
    }


    // 이름의 중복 검증
    @Transactional(readOnly = true)
    private void validateDuplicateName(String name){
        Optional<Member> findMember = memberRepository.findByName(name);
        if (findMember.isPresent()) {
            throw new DuplicateNameException(DUPLICATE_NAME.getMessage());
        }
    }

    // 이름으로 찾아서 반환
    @Transactional(readOnly = true)
    public Member findMember(String name){
        return memberRepository.findByName(name)
                .orElseThrow(()->new MemberNotFoundException(MEMBER_NAME_NOT_FOUND.getMessage()));
    }

}
