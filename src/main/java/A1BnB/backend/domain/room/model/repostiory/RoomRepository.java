package A1BnB.backend.domain.room.model.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;
import A1BnB.backend.domain.room.model.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
