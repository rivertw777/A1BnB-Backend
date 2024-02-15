package A1BnB.backend.domain.file.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile("local")
@Service
public class FileSystemService implements FileService {

    @Autowired
    @Value("${photo.save.dir}")
    private String saveDir;

    @Autowired
    @Value("${photo.access.url}")
    private String accessUrl;

    @Override
    public List<String> uploadPhoto(List<MultipartFile> photos, List<String> photoNames) throws IOException {
        // 사진 저장 경로
        String savePath = System.getProperty("user.dir") + saveDir;
        // 사진 접근 경로
        List<String> accessUrls = new ArrayList<>();
        int index = 0;
        for (MultipartFile photo : photos) {
            File saveFile = new File(savePath, photoNames.get(index));
            photo.transferTo(saveFile);
            accessUrls.add(accessUrl + photoNames.get(index));
            index++;
        }
        return accessUrls;
    }

}