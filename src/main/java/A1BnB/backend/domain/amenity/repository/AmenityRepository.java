package A1BnB.backend.domain.amenity.repository;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
