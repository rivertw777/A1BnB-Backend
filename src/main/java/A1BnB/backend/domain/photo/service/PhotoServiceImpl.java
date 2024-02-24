package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.ammenity.model.entity.Ammenity;
import A1BnB.backend.domain.ammenity.service.AmmenityService;
import A1BnB.backend.domain.photo.dto.request.PhotoUploadRequest;
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
@Transactional
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private final S3Service s3Service;
    @Autowired
    private final AmmenityService ammenityService;
    @Autowired
    private final PhotoRepository photoRepository;
    @Autowired
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

        // ammenity 리스트 반환
        List<Ammenity> ammenities = getAmmenities(objectList);

        // photo 엔티티 저장
        Photo photo = getPhoto(imageUrl, ammenities);
        photoIdList.add(photo.getPhotoId());
    }

    // photo 엔티티 저장
    private Photo getPhoto(String imageUrl, List<Ammenity> ammenities) {
        String detectedUrl = getDetectedUrl(imageUrl);
        Photo photo = Photo.builder()
                .originalUrl(imageUrl)
                .detectedUrl(detectedUrl)
                .ammenities(ammenities)
                .build();
        photoRepository.save(photo);
        return photo;
    }

    // 분석된 사진 경로 반환
    private String getDetectedUrl(String originalUrl){
        String photoName = originalUrl.substring(originalUrl.indexOf("photos/") + "photos/".length());
        return detecetedUrl + photoName;
    }

    // ammenity 리스트 반환
    private List<Ammenity> getAmmenities(List<Map<String, Double>> objectList) {
        List<Ammenity> ammenities = new ArrayList<>();
        for (Map<String, Double> objectMap : objectList) {
            // ammenity 추가
            addAmmenity(ammenities, objectMap);
        }
        return ammenities;
    }

    // ammenity 추가
    private void addAmmenity(List<Ammenity> ammenities, Map<String, Double> objectMap) {
        for (Map.Entry<String, Double> objectEntry : objectMap.entrySet()) {
            String type = objectEntry.getKey();
            double confidence = objectEntry.getValue();
            // ammenity 엔티티 저장
            Ammenity ammenity = ammenityService.saveAmmenity(type, confidence);
            ammenities.add(ammenity);
        }
    }

}
