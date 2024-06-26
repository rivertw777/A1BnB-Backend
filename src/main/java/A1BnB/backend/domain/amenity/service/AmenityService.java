package A1BnB.backend.domain.amenity.service;

import A1BnB.backend.domain.amenity.model.Amenity;
import java.util.List;
import java.util.Map;

public interface AmenityService {
    List<Amenity> saveAmenities(Map<String, Double> amenitiesInfo);
}
