package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.amenity.service.AmenityService;
import A1BnB.backend.domain.photo.dto.request.InferenceResultRequest;
import A1BnB.backend.domain.photo.dto.PhotoInfo;
import A1BnB.backend.domain.photo.dto.request.PhotoUploadRequest;
import A1BnB.backend.domain.photo.dto.response.InferenceResultResponse;
import A1BnB.backend.domain.photo.dto.mapper.PhotoInfoMapper;
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
    private final PhotoInfoMapper photoInfoMapper;
    private final JsonParser jsonParser;

    @Value("${photo.detected.url}")
    private String detecetedUrl;

    // 사진 s3 업로드
    @Override
    public List<String> uploadPhotos(PhotoUploadRequest requestParam) throws IOException {
        List<String> photoNames = makePhotoNames(requestParam.photos());
        return s3Service.uploadPhotos(requestParam.photos(), photoNames);
    }

    // 사진 이름 반환
    private List<String> makePhotoNames(List<MultipartFile> photos) {
        return photos.stream()
                .map(photo -> UUID.randomUUID() + "_" + photo.getOriginalFilename())
                .collect(Collectors.toList());
    }

    // 사진 저장, PhotoId 리스트 반환
    @Override
    @Transactional
    public List<Long> savePhotos(String inferenceResult) throws JsonProcessingException {
        Map<String, Map<String, Map<String, Double>>> parsedData = jsonParser.parseInferenceResult(inferenceResult);

        List<Long> photoIdList = new ArrayList<>();
        for (Map.Entry<String, Map<String, Map<String, Double>>> entry : parsedData.entrySet()) {
            String imageUrl = entry.getKey();
            Map<String, Double> roomInfo = entry.getValue().get("room");
            Map<String, Double> amenitiesInfo = entry.getValue().get("amenities");

            Room room = roomService.saveRoom(roomInfo);
            List<Amenity> amenities = amenityService.saveAmenities(amenitiesInfo);

            Photo photo = savePhoto(imageUrl, room, amenities);
            photoIdList.add(photo.getId());
        }
        return photoIdList;
    }

    // Photo 엔티티 저장
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

    // 사진 추론 결과 DTO 리스트 반환
    @Override
    @Transactional(readOnly = true)
    public List<InferenceResultResponse> getInferenceResults(InferenceResultRequest requestParam) {
        List<Photo> photos = findPhotos(requestParam.photoIdList());
        return resultResponseMapper.toResultResponses(photos);
    }

    // Photo 리스트 반환
    @Transactional(readOnly = true)
    public List<Photo> findPhotos(List<Long> photoIds) {
        return photoRepository.findAllByIdIn(photoIds);
    }

    // 사진 정보 DTO 리스트 반환
    @Override
    public List<PhotoInfo> getPhotoInfos(List<Photo> photos) {
        return photoInfoMapper.toPhotoInfoList(photos);
    }

}
