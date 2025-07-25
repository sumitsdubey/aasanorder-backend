package com.sumit.aasanorder_backend.controllers;

import com.mongodb.client.result.UpdateResult;
import com.sumit.aasanorder_backend.enus.Status;
import com.sumit.aasanorder_backend.sevices.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @PutMapping("/aproove/{username}")
    public ResponseEntity<?> approveUser(Authentication auth, @PathVariable String username) {
        try{
            UpdateResult updateResult = adminService.updateUserStatus(username, Status.ACTIVE.toString());
            return new ResponseEntity<>(updateResult, HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
