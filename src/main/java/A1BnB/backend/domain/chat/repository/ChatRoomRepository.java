package A1BnB.backend.domain.chat.repository;

import A1BnB.backend.domain.chat.model.ChatRoom;
import A1BnB.backend.domain.member.model.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @Query("SELECT cr FROM ChatRoom cr WHERE :member1 MEMBER OF cr.participants AND :member2 MEMBER OF cr.participants")
    Optional<ChatRoom> findChatRoomByParticipants(@Param("member1") Member member1, @Param("member2") Member member2);
    List<ChatRoom> findByParticipants(Member participant);
}
