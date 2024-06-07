package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.photo.dto.PhotoDto.InferenceResultRequest;
import A1BnB.backend.domain.photo.dto.PhotoDto.PhotoInfo;
import A1BnB.backend.domain.photo.dto.PhotoDto.PhotoUploadRequest;
import A1BnB.backend.domain.photo.dto.PhotoDto.InferenceResultResponse;
import A1BnB.backend.domain.photo.model.Photo;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PhotoService {
    List<String> uploadPhotos(PhotoUploadRequest requestParam) throws IOException;
    List<Long> savePhotos(String inferenceResult) throws JsonProcessingException;
    List<InferenceResultResponse> getInferenceResults(InferenceResultRequest requestParam);
    List<Photo> findPhotos(List<Long> photoIds);
    List<PhotoInfo> getPhotoInfos(List<Photo> photos);

}
