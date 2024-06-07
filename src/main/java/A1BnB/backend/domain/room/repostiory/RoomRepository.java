package A1BnB.backend.domain.room.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import A1BnB.backend.domain.room.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
