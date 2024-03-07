package A1BnB.backend.domain.photo.repository;

import A1BnB.backend.domain.photo.model.entity.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByIdIn(List<Long> ids);
}
