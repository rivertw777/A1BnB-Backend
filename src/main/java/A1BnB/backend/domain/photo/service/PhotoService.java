package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.photo.model.entity.Photo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PhotoService {
    List<Photo> savePhotos(List<String> photoUrls);
}
