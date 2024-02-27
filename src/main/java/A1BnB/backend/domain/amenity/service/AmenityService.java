package A1BnB.backend.domain.amenity.service;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import org.springframework.stereotype.Service;

@Service
public interface AmenityService {
    Amenity saveAmenity(String type, Double confidence);
}
