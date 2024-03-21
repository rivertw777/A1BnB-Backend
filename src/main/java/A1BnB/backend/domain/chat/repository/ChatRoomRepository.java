package A1BnB.backend.domain.chat.repository;

import A1BnB.backend.domain.chat.model.ChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByHostAndGuest(String host, String guest);

}
