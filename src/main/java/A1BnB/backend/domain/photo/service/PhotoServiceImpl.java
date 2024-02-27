package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.amenity.model.entity.Amenity;
import A1BnB.backend.domain.amenity.service.AmenityService;
import A1BnB.backend.domain.photo.dto.PhotoUploadRequest;
import A1BnB.backend.domain.photo.dto.InferenceResultResponse;
import A1BnB.backend.domain.photo.dto.mapper.ResultResponseMapper;
import A1BnB.backend.domain.photo.model.entity.Photo;
import A1BnB.backend.domain.photo.repository.PhotoRepository;
import A1BnB.backend.domain.photo.utils.JsonParser;
import A1BnB.backend.global.s3.service.S3Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private final S3Service s3Service;
    @Autowired
    private final AmenityService amenityService;
    @Autowired
    private final PhotoRepository photoRepository;
    @Autowired
    private final JsonParser jsonParser;
    @Autowired
    private final ResultResponseMapper resultResponseMapper;

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
        // 문자열 파싱
        Map<String, List<Map<String, Double>>> parsedResult = jsonParser.parseInferenceResult(inferenceResult);

        List<Long> photoIdList = new ArrayList<>();
        for (Map.Entry<String, List<Map<String, Double>>> entry : parsedResult.entrySet()) {
            handleEntry(photoIdList, entry);
        }
        // photoId 리스트
        return photoIdList;
    }

    private void handleEntry(List<Long> photoIdList, Entry<String, List<Map<String, Double>>> entry) {
        String imageUrl = entry.getKey();
        List<Map<String, Double>> objectList = entry.getValue();

        // amenity 리스트 반환
        List<Amenity> amenities = getAmenities(objectList);

        // photo 엔티티 저장
        Photo photo = savePhoto(imageUrl, amenities);
        photoIdList.add(photo.getPhotoId());
    }

    // photo 엔티티 저장
    @Transactional
    private Photo savePhoto(String imageUrl, List<Amenity> amenities) {
        String detectedUrl = getDetectedUrl(imageUrl);
        Photo photo = Photo.builder()
                .originalUrl(imageUrl)
                .detectedUrl(detectedUrl)
                .amenities(amenities)
                .build();
        photoRepository.save(photo);
        return photo;
    }

    // 분석된 사진 경로 반환
    private String getDetectedUrl(String originalUrl){
        String photoName = originalUrl.substring(originalUrl.indexOf("photos/") + "photos/".length());
        return detecetedUrl + photoName;
    }

    // amenity 리스트 반환
    private List<Amenity> getAmenities(List<Map<String, Double>> objectList) {
        List<Amenity> amenities = new ArrayList<>();
        for (Map<String, Double> objectMap : objectList) {
            // amenity 추가
            addAmenity(amenities, objectMap);
        }
        return amenities;
    }

    // amenity 추가
    private void addAmenity(List<Amenity> amenities, Map<String, Double> objectMap) {
        for (Map.Entry<String, Double> objectEntry : objectMap.entrySet()) {
            String type = objectEntry.getKey();
            double confidence = objectEntry.getValue();
            // amenity 엔티티 저장
            Amenity amenity = amenityService.saveAmenity(type, confidence);
            amenities.add(amenity);
        }
    }

    @Override
    public List<InferenceResultResponse> getInferenceResults(List<Long> photoIdList) {
        // 사진 리스트 조회
        List<Photo> photos = getPhotos(photoIdList);
        // // 결과 응답 DTO 반환
        return resultResponseMapper.toResultResponses(photos);
    }

    // 사진 리스트 조회
    @Transactional(readOnly = true)
    public List<Photo> getPhotos(List<Long> photoIdList) {
        return photoRepository.findAllByIdIn(photoIdList);
    }

}
