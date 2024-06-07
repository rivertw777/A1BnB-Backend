package A1BnB.backend.domain.amenity.service;

import A1BnB.backend.domain.amenity.model.Amenity;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface AmenityService {
    List<Amenity> saveAmenities(Map<String, Double> amenitiesInfo);
}
