package A1BnB.backend.member.service;

import A1BnB.backend.member.dto.request.MemberSignupRequest;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void registerUser(MemberSignupRequest signupParam);

}