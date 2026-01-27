package com.canhhocit.learn_twohours.Services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageStorgaeService implements IStorageService {
    // Path storageFolder = Paths.get("uploads");
    Path storageFolder = Paths.get(System.getProperty("user.dir"), "uploads");
    // Đặt uploads cùng cấp với pom.xml
    // Path storageFolder = Paths.get("pom.xml")
    // .toAbsolutePath()
    // .getParent()
    // .resolve("uploads");

    // contructor : dc goi khi inject service nay
    public ImageStorgaeService() {
        try {
            Files.createDirectories(storageFolder);

        } catch (Exception e) {
            throw new RuntimeException("cannot initialize storage", e);
        }
    }

@Override
public void deleteAllFiles() {
    try {
        // Lấy danh sách tất cả file trong thư mục uploads
        Files.list(storageFolder).forEach(file -> {
            try {
                // Xóa từng file
                Files.deleteIfExists(file);
            } catch (IOException e) {
                throw new RuntimeException("Cannot delete file: " + file.getFileName(), e);
            }
        });
    } catch (IOException e) {
        throw new RuntimeException("Cannot delete all files", e);
    }
}


   @Override
public Stream<Path> loadAll() {
    try {
        // Lấy tất cả file trong thư mục uploads
        return Files.list(storageFolder)
                // Chỉ lấy tên file, không lấy cả đường dẫn
                .map(Path::getFileName);
    } catch (IOException e) {
        throw new RuntimeException("Cannot load uploaded files", e);
    }
}


    @Override
public byte[] readFileContent(String fileName) {
    try {
        // Ghép tên file với thư mục uploads
        Path filePath = storageFolder.resolve(fileName);

        if (!Files.exists(filePath)) {
            throw new RuntimeException("File not found: " + fileName);
        }

        // read all content file
        return Files.readAllBytes(filePath);
    } catch (IOException e) {
        throw new RuntimeException("Cannot read file: " + fileName, e);
    }
}


    @Override
    public String storeFile(MultipartFile file) {
        try {
            System.out.println("hehe");
            System.out.println("Upload dir: " + storageFolder.toAbsolutePath());
            if (file.isEmpty()) {
                throw new RuntimeException("file is empty");
            }
            // check file is image
            if (!isImageFile(file)) {
                throw new RuntimeException("file isn't image file");
            }
            // file must be <=5mb
            float fileSizeMegaBytes = file.getSize() / 1_000_000.0f;
            if (fileSizeMegaBytes > 5.0f) {
                throw new RuntimeException("file must be <=5Mb");
            }
            // File must be renamed to avoid being overwritten when saved on the server

            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-", "");
            generatedFileName = generatedFileName + "." + fileExtension;

            // nơi nhận cuối cùng
            Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize()
                    .toAbsolutePath();
            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                // sercurity check
                throw new RuntimeException("Cannot store file ouside current dictionary.");
            }
            // Copy the uploaded file to the destination path, replacing the existing file
            // if any
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Cannot store file", e);
            }

            return generatedFileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save file", e);
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[] { "png", "jpg", "jpeg", "bmp" })
                .contains(fileExtension.trim().toLowerCase());
    }

}
