package A1BnB.backend.domain.room.service;

import A1BnB.backend.domain.room.model.Room;
import A1BnB.backend.domain.room.repostiory.RoomRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    // Room 반환
    @Override
    public Room saveRoom(Map<String, Double> roomInfo) {
        String roomType = roomInfo.keySet().iterator().next();
        Double probability = roomInfo.get(roomType);
        Room room = saveRoom(roomType, probability);
        return room;
    }

    // Room 엔티티 저장
    @Transactional
    private Room saveRoom(String roomType, Double probability) {
        Room room = Room.builder()
                .type(roomType)
                .probability(probability)
                .build();
        roomRepository.save(room);
        return room;
    }

}
