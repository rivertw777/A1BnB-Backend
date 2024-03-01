package A1BnB.backend.domain.photo.repository;

import A1BnB.backend.domain.photo.model.entity.Photo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Optional<Photo> findByPhotoId(Long photoId);

    @Query("SELECT p FROM Photo p WHERE p.id IN :ids")
    List<Photo> findAllByIdList(@Param("ids") List<Long> ids);
}
