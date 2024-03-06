package A1BnB.backend.domain.room.service;

import A1BnB.backend.domain.room.model.entity.Room;
import java.util.Map;

public interface RoomService {
    Room saveRoom(Map<String, Double> roomInfo);
}
