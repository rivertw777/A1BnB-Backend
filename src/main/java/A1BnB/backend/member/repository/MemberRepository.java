package A1BnB.backend.member.repository;

import A1BnB.backend.member.model.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
    Optional<Member> findByMemberId(Long memberId);

}