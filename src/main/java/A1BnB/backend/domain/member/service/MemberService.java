package A1BnB.backend.domain.member.service;

import A1BnB.backend.domain.member.dto.request.MemberSignupRequest;
import A1BnB.backend.domain.member.dto.response.MyPostReservationResponse;
import A1BnB.backend.domain.member.model.entity.Member;
import A1BnB.backend.domain.post.dto.response.PostResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    void registerUser(MemberSignupRequest signupParam);
    Member findMember(String name);
    List<PostResponse> findMyPosts(String username);
    List<MyPostReservationResponse> findPostReservations(String username);
}