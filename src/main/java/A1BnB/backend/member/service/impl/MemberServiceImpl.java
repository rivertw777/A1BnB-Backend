package A1BnB.backend.member.service.impl;

import static A1BnB.backend.member.exception.constants.MemberExceptionMessages.DUPLICATE_NAME;

import A1BnB.backend.member.dto.request.MemberSignupRequest;
import A1BnB.backend.member.exception.DuplicateNameException;
import A1BnB.backend.member.model.Role;
import A1BnB.backend.member.model.entity.Member;
import A1BnB.backend.member.service.MemberService;
import A1BnB.backend.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Override
    public void registerUser(MemberSignupRequest signupParam) {
        // 이름 중복 검증
        validateDuplicateName(signupParam.name());

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(signupParam.password());

        // 회원 저장
        Member member = Member.builder()
                .name(signupParam.name())
                .password(encodedPassword)
                .roles(Collections.singletonList(Role.USER))
                .build();
        memberRepository.save(member);
    }

    // 이름의 중복 검증
    private void validateDuplicateName(String username){
        Optional<Member> findMember = memberRepository.findByName(username);
        if (findMember.isPresent()) {
            throw new DuplicateNameException(DUPLICATE_NAME.getMessage());
        }
    }

}
