package A1BnB.backend.domain.photo.controller;

import A1BnB.backend.domain.photo.dto.PhotoDto.PhotoUploadRequest;
import A1BnB.backend.domain.photo.dto.PhotoDto.InferenceResultRequest;
import A1BnB.backend.domain.photo.dto.PhotoDto.InferenceResultResponse;
import A1BnB.backend.domain.photo.service.PhotoService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/photos")
public class PhotoController {

    private final PhotoService photoService;

    // 사진 추론
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Long> inferPhotos(@Valid @ModelAttribute PhotoUploadRequest requestParam) throws IOException {
        return photoService.inferPhotos(requestParam);
    }

    // 분석 결과 반환
    @PostMapping("/results")
    public List<InferenceResultResponse> getInferenceResults(@Valid @RequestBody InferenceResultRequest requestParam) {
        return photoService.getInferenceResults(requestParam);
    }

}