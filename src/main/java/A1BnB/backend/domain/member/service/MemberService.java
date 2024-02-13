package A1BnB.backend.domain.member.service;

import A1BnB.backend.domain.member.dto.request.MemberSignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void registerUser(MemberSignupRequest signupParam);

}