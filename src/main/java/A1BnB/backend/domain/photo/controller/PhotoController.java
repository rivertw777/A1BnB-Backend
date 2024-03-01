package A1BnB.backend.domain.photo.controller;

import A1BnB.backend.domain.photo.dto.PhotoUploadRequest;
import A1BnB.backend.domain.photo.dto.InferenceResultRequest;
import A1BnB.backend.domain.photo.dto.InferenceResultResponse;
import A1BnB.backend.domain.photo.service.PhotoService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/photos")
public class PhotoController {

    private final PhotoService photoService;
    private final WebClient webClient;

    // Lambda API
    @Value("${aws.lambda.url}")
    private String lambdaUrl;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> inferPhotos(@Valid @ModelAttribute PhotoUploadRequest uploadParam) throws IOException {
        // s3 사진 업로드, 경로 반환
        List<String> photoUrls = photoService.uploadPhotos(uploadParam);
        // Lambda POST 요청
        String inferenceResult = postLambda(photoUrls);
        // Photo 엔티티 저장, photoIdList 반환
        return photoService.savePhotos(inferenceResult);
    }

    // Lambda POST 요청
    private String postLambda(List<String> photoUrls){
        return webClient.post()
                .uri(lambdaUrl)
                .bodyValue(photoUrls)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    // 분석 결과 반환
    @PostMapping("/results")
    public List<InferenceResultResponse> getResults(@Valid @RequestBody InferenceResultRequest requestParam) {
        return photoService.getInferenceResults(requestParam);
    }

}