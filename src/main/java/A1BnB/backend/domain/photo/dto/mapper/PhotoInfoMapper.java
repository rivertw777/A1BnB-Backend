package A1BnB.backend.domain.photo.dto.mapper;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.photo.dto.PhotoDto.PhotoInfo;
import A1BnB.backend.domain.photo.model.entity.Photo;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PhotoInfoMapper {
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

}


