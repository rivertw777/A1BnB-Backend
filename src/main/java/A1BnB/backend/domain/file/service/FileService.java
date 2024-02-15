package A1BnB.backend.domain.file.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadPhoto(MultipartFile[] photos, String photoName) throws IOException;
}