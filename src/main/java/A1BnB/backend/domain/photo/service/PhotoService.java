package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.photo.dto.request.PhotoUploadRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PhotoService {
    List<String> uploadPhotos(PhotoUploadRequest uploadParam) throws IOException;
    List<Long> savePhotos(String inferenceResult) throws JsonProcessingException;
}
