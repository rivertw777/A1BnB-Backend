package A1BnB.backend.domain.date.repository;

import A1BnB.backend.domain.date.model.Date;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DateRepository extends JpaRepository<Date, Long> {
}

