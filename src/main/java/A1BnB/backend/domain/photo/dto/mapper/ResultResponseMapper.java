package A1BnB.backend.domain.photo.dto.mapper;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.photo.dto.ResultResponse;
import A1BnB.backend.domain.photo.model.entity.Photo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ResultResponseMapper {

    public List<ResultResponse> toResultResponses(List<Photo> photos) {
        return photos.stream()
                .map(photo -> toResultResponse(photo))
                .collect(Collectors.toList());
    }

    public ResultResponse toResultResponse(Photo photo) {
        List<Amenity> amenities = photo.getAmenities();
        List<String> amenityTypes = amenities.stream()
                .map(Amenity::getType)
                .collect(Collectors.toList());
        return new ResultResponse(
                photo.getPhotoId(),
                "kitchen",
                photo.getDetectedUrl(),
                amenityTypes
        );
    }

}
