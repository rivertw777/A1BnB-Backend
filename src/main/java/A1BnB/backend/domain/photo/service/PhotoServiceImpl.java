package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.amenity.service.AmenityService;
import A1BnB.backend.domain.photo.dto.InferenceResultRequest;
import A1BnB.backend.domain.photo.dto.PhotoUploadRequest;
import A1BnB.backend.domain.photo.dto.InferenceResultResponse;
import A1BnB.backend.domain.photo.dto.mapper.ResultResponseMapper;
import A1BnB.backend.domain.photo.model.entity.Photo;
import A1BnB.backend.domain.photo.repository.PhotoRepository;
import A1BnB.backend.domain.photo.utils.JsonParser;
import A1BnB.backend.domain.room.model.entity.Room;
import A1BnB.backend.domain.room.service.RoomService;
import A1BnB.backend.global.s3.service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PhotoServiceImpl implements PhotoService {

    private final S3Service s3Service;
    private final AmenityService amenityService;
    private final RoomService roomService;
    private final PhotoRepository photoRepository;
    private final ResultResponseMapper resultResponseMapper;
    private final JsonParser jsonParser;

    @Value("${photo.detected.url}")
    private String detecetedUrl;

    @Override
    public List<String> uploadPhotos(PhotoUploadRequest uploadParam) throws IOException {
        // 사진 경로 반환
        List<String> photoNames = makePhotoNames(uploadParam.photos());
        List<String> photoUrls = s3Service.uploadPhotos(uploadParam.photos(), photoNames);
        return photoUrls;
    }

    private List<String> makePhotoNames(List<MultipartFile> photos) {
        return photos.stream()
                .map(photo -> UUID.randomUUID() + "_" + photo.getOriginalFilename())
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> savePhotos(String inferenceResult) throws JsonProcessingException {
        Map<String, Map<String, Map<String, Double>>> parsedData = jsonParser.parseInferenceResult(inferenceResult);

        List<Long> photoIdList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Map<String, Double>>> entry : parsedData.entrySet()) {
            String imageUrl = entry.getKey();
            Map<String, Double> roomInfo = entry.getValue().get("room");
            Map<String, Double> amenitiesInfo = entry.getValue().get("amenities");

            Room room = roomService.getRoom(roomInfo);
            List<Amenity> amenities = amenityService.getAmenities(amenitiesInfo);

            Photo photo = savePhoto(imageUrl, room, amenities);
            photoIdList.add(photo.getPhotoId());
        }
        return photoIdList;
    }

    @Transactional
    private Photo savePhoto(String imageUrl, Room room, List<Amenity> amenities) {
        Photo photo = Photo.builder()
                .originalUrl(imageUrl)
                .detectedUrl(getDetectedUrl(imageUrl))
                .amenities(amenities)
                .room(room)
                .build();
        photoRepository.save(photo);
        return photo;
    }


    // 분석된 사진 경로 반환
    private String getDetectedUrl(String originalUrl){
        String photoName = originalUrl.substring(originalUrl.indexOf("photos/") + "photos/".length());
        return detecetedUrl + photoName;
    }


    // 추론 결과 반환
    @Override
    public List<InferenceResultResponse> getInferenceResults(InferenceResultRequest requestParam) {
        List<Photo> photos = getPhotos(requestParam.photoIdList());
        return resultResponseMapper.toResultResponses(photos);
    }

    // 사진 리스트 조회
    @Transactional(readOnly = true)
    public List<Photo> getPhotos(List<Long> photoIdList) {
        return photoRepository.findAllByIdList(photoIdList);
    }

}
