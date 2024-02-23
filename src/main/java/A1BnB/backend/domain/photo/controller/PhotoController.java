package A1BnB.backend.domain.photo.controller;

import A1BnB.backend.domain.photo.dto.request.PhotoUploadRequest;
import A1BnB.backend.domain.photo.service.PhotoService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/photos")
public class PhotoController {

    @Autowired
    private final PhotoService photoService;
    @Autowired
    private final WebClient webClient;

    // Lambda API
    @Value("${aws.lambda.url}")
    private String lambdaUrl;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> savePhotos(@Valid @ModelAttribute PhotoUploadRequest uploadParam) throws IOException {
        // 사진 업로드, 경로 반환
        List<String> photoUrls = photoService.uploadPhotos(uploadParam);

        // Lambda POST 요청
        String inferenceResult = postPhotosToLambda(photoUrls);

        // 사진 엔티티 저장
        photoService.savePhotos(inferenceResult);
        return ResponseEntity.ok().build();
    }

    // Lambda POST 요청
    private String postPhotosToLambda(List<String> photoUrls){
        return webClient.post()
                .uri(lambdaUrl)
                .bodyValue(photoUrls)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}