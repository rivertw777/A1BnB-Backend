package A1BnB.backend.domain.photo.dto;

import A1BnB.backend.domain.amenity.model.Amenity;
import A1BnB.backend.domain.photo.dto.PhotoDto.InferenceResultResponse;
import A1BnB.backend.domain.photo.dto.PhotoDto.PhotoInfo;
import A1BnB.backend.domain.photo.model.Photo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PhotoDtoMapper {

    // 사진 정보 DTO 변환
    public List<PhotoInfo> toPhotoInfoList(List<Photo> photos) {
        return photos.stream()
                .map(photo -> toPhotoInfo(photo))
                .collect(Collectors.toList());
    }
    public PhotoInfo toPhotoInfo(Photo photo) {
        return new PhotoInfo(
                photo.getOriginalUrl(),
                photo.getDetectedUrl(),
                photo.getRoom().getType(),
                getAmenityTypes(photo.getAmenities())
        );
    }
    private List<String> getAmenityTypes(List<Amenity> amenities){
        return amenities.stream()
                .map(Amenity::getType)
                .collect(Collectors.toList());
    }

    // 추론 결과 응답 DTO 변환
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

}
