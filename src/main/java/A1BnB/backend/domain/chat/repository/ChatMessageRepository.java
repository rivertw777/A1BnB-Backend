package A1BnB.backend.domain.chat.repository;

import A1BnB.backend.domain.chat.model.ChatMessage;
import A1BnB.backend.domain.chat.model.ChatRoom;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Optional<ChatMessage> findFirstByChatRoomOrderByCreatedAtDesc(ChatRoom chatRoom);
}
