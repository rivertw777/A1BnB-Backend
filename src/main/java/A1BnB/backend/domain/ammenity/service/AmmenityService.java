package A1BnB.backend.domain.ammenity.service;

import A1BnB.backend.domain.ammenity.model.entity.Ammenity;
import org.springframework.stereotype.Service;

@Service
public interface AmmenityService {
    Ammenity saveAmmenity(String type, Double confidence);
}
