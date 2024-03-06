package A1BnB.backend.domain.date.service;

import A1BnB.backend.domain.post.model.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AvailableDateService {
    void saveAvailableDates(Post post, List<LocalDateTime> availableDates);
    void deleteAvailableDates(Post post, List<LocalDateTime> bookedDates);
}
