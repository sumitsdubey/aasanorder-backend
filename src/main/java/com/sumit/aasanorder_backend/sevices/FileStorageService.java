package com.sumit.aasanorder_backend.sevices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.max-size}")
    private long maxSize;

    @Value("${file.allowed-types}")
    private String[] allowedTypes;


    public String storeFile(MultipartFile file) throws IOException {
        // Validate file
        if (file ==null) {
            throw new IllegalArgumentException("File is empty");
        }

        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("File size exceeds maximum limit");
        }

        if (!Arrays.asList(allowedTypes).contains(file.getContentType())) {
            throw new IllegalArgumentException("File type not allowed");
        }

//        // Virus scanning
//        if (!virusScanner.isSafe(file.getBytes())) {
//            throw new IllegalArgumentException("File contains malware");
//        }
//
//        // More thorough content verification
//        if (!isActuallyAnImage(file.getBytes())) {
//            throw new IllegalArgumentException("File is not a valid image");
//        }

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Save file
        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath);

        return filename;
    }

    public Resource loadFile(String filename) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(filename);
        Resource resource = new FileSystemResource(filePath);
        return resource;
    }
}
