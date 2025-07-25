package com.sumit.aasanorder_backend.controllers;

import com.sumit.aasanorder_backend.entities.User;
import com.sumit.aasanorder_backend.models.ApiResponseModel;
import com.sumit.aasanorder_backend.sevices.AWSFileUploadService;
import com.sumit.aasanorder_backend.sevices.FileStorageService;
import com.sumit.aasanorder_backend.sevices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private AWSFileUploadService awsFileUploadService;

    @GetMapping
    public ResponseEntity<?> getMyProfile(Authentication authentication) {
        try{
            User user = userService.getUser(authentication.getName());
            if(user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>("User Not Found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/image")
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile file, Authentication authentication) {
        try{
            String filePath = awsFileUploadService.uploadFile(file);
            String username = authentication.getName();
            userService.uploadImage(username, filePath);
            return new ResponseEntity<>(new ApiResponseModel(filePath, "Profile Changed Success", 204, true), HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel(null, e.getMessage(), 500, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

