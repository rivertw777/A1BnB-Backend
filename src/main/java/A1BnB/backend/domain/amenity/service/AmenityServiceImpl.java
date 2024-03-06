package A1BnB.backend.domain.amenity.service;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.amenity.repository.AmenityRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;

    // Amenity 리스트 반환
    @Override
    public List<Amenity> saveAmenities(Map<String, Double> amenitiesInfo) {
        return amenitiesInfo
                .entrySet()
                .stream()
                .map(entry -> saveAmenity(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    // Amenity 엔티티 저장
    @Transactional
    private Amenity saveAmenity(String type, Double confidence) {
        Amenity amenity = Amenity.builder()
                .type(type)
                .confidence(confidence)
                .build();
        amenityRepository.save(amenity);
        return amenity;
    }

}
