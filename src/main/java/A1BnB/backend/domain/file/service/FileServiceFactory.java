package A1BnB.backend.domain.file.service;

public class FileServiceFactory {
    public static FileService create(String profile) {
        if (profile.equals("local")) {
            return new FileSystemService();
        }
        return null;
    }
}
