package com.sumit.aasanorder_backend.sevices;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AWSFileUploadService {
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {
        String fileName = "profile"+ UUID.randomUUID().toString() +"_"+ file.getOriginalFilename();

        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

            return amazonS3.getUrl(bucketName, fileName).toString();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to upload file", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to Upload File " +e);
        }
    }

}
