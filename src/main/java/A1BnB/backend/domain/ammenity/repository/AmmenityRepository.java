package A1BnB.backend.domain.ammenity.repository;

import A1BnB.backend.domain.ammenity.model.entity.Ammenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmmenityRepository extends JpaRepository<Ammenity, Long> {
}
