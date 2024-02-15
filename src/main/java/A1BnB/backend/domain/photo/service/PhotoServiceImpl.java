package A1BnB.backend.domain.photo.service;

import A1BnB.backend.domain.photo.model.entity.Photo;
import A1BnB.backend.domain.photo.repository.PhotoRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private final PhotoRepository photoRepository;
    @Override
    public List<Photo> savePhotos(List<String> photoUrls) {
        return photoUrls.stream()
                .map(url -> Photo.builder().accessUrl(url).build())
                .map(photoRepository::save)
                .collect(Collectors.toList());
    }

}
