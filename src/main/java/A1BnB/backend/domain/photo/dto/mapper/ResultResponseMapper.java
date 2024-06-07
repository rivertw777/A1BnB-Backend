package A1BnB.backend.domain.photo.dto.mapper;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.photo.dto.PhotoDto.InferenceResultResponse;
import A1BnB.backend.domain.photo.model.entity.Photo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ResultResponseMapper {

    public List<InferenceResultResponse> toResultResponses(List<Photo> photos) {
        return photos.stream()
                .map(photo -> toResultResponse(photo))
                .collect(Collectors.toList());
    }

    public InferenceResultResponse toResultResponse(Photo photo) {
        return new InferenceResultResponse(
                photo.getId(),
                photo.getRoom().getType(),
                photo.getDetectedUrl(),
                getAmenityTypes(photo.getAmenities())
        );
    }

    private List<String> getAmenityTypes (List<Amenity> amenities) {
        return amenities.stream()
                .map(Amenity::getType)
                .collect(Collectors.toList());
    }

}
