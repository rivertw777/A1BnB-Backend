package A1BnB.backend.domain.date.repository;

import A1BnB.backend.domain.date.model.entity.AvailableDate;
import A1BnB.backend.domain.post.model.entity.Post;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {
    void deleteByPostAndDate(Post post, LocalDateTime date);
}

