package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.photo.dto.PhotoUploadRequest;
import A1BnB.backend.domain.photo.dto.InferenceResultResponse;
import A1BnB.backend.domain.photo.model.entity.Photo;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PhotoService {
    List<String> uploadPhotos(PhotoUploadRequest uploadParam) throws IOException;
    List<Long> savePhotos(String inferenceResult) throws JsonProcessingException;
    List<InferenceResultResponse> getInferenceResults(List<Long> photoIdList);
    List<Photo> getPhotos(List<Long> photoIdList);
}
