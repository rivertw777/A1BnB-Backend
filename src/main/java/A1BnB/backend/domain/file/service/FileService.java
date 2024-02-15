package A1BnB.backend.domain.file.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    List<String> uploadPhoto(List<MultipartFile> photos, List<String> photoNames) throws IOException;
}