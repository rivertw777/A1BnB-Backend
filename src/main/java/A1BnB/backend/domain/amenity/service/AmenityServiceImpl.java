package A1BnB.backend.domain.amenity.service;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.amenity.repository.AmenityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    @Autowired
    private final AmenityRepository amenityRepository;

    @Override
    public Amenity saveAmenity(String type, Double confidence) {
        Amenity amenity = Amenity.builder()
                .type(type)
                .confidence(confidence)
                .build();
        amenityRepository.save(amenity);
        return amenity;
    }

}
