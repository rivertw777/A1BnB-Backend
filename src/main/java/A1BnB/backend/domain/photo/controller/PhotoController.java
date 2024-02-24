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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/photos")
public class PhotoController {

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    private final PhotoService photoService;
    @Autowired
    private final WebClient webClient;

    // Lambda API
    @Value("${aws.lambda.url}")
    private String lambdaUrl;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> savePhotos(@Valid @ModelAttribute PhotoUploadRequest uploadParam) throws IOException {
        // 사진 업로드, 경로 반환
        List<String> photoUrls = photoService.uploadPhotos(uploadParam);

        // Lambda POST 요청
        String inferenceResult = postLambda(photoUrls);

        // 사진 엔티티 저장
        List<Long> photoIdList = photoService.savePhotos(inferenceResult);

        // photoIdList 반환
        return ResponseEntity.ok(photoIdList);
    }

    // Lambda POST 요청
    public String postLambda(List<String> photoUrls){
        long start = System.currentTimeMillis();
        String result = webClient.post()
                .uri(lambdaUrl)
                .bodyValue(photoUrls)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("postLambda - Execution Time: " + executionTime + "ms");
        return result;
    }

}