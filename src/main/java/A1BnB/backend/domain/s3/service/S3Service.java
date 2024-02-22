package A1BnB.backend.domain.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class S3Service {

    @Autowired
    private final AmazonS3 amazonS3;

    @Autowired
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadPhoto(List<MultipartFile> photos, List<String> photoNames) throws IOException {
        List<String> photoUrls = new ArrayList<>();

        for (int i = 0; i < photos.size(); i++) {
            MultipartFile photo = photos.get(i);
            String photoName = photoNames.get(i);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(photo.getSize());
            metadata.setContentType(photo.getContentType());

            amazonS3.putObject(bucket, photoName, photo.getInputStream(), metadata);
            String photoUrl = amazonS3.getUrl(bucket, photoName).toString();
            photoUrls.add(photoUrl);
        }
        return photoUrls;
    }

}