package com.sumit.tableserve_backend.controllers;


import com.sumit.tableserve_backend.dto.ShopRequest;
import com.sumit.tableserve_backend.entities.Shop;
import com.sumit.tableserve_backend.models.ApiResponseModel;
import com.sumit.tableserve_backend.sevices.AWSFileUploadService;
import com.sumit.tableserve_backend.sevices.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private AWSFileUploadService awsFileUploadService;


    @PostMapping("/create")
    public ResponseEntity<?> create(@ModelAttribute ShopRequest requestShop, Authentication auth) {
        String username = auth.getName();
        boolean hasBlank = Stream.of(
                        requestShop.getShopName(),
                        requestShop.getShopAddress(),
                        requestShop.getShopCity(),
                        requestShop.getIdenity(),
                        requestShop.getPhone(),
                        requestShop.getEmail(),
                        requestShop.getDescription())
                .anyMatch(s -> s.isEmpty() || s ==null);
        if(hasBlank) {
            return new ResponseEntity<>(new ApiResponseModel(null, "All Feilds are Required", 401, false), HttpStatus.BAD_REQUEST);
        }
        String fileUrl="";
        if(requestShop.getImage()!=null){
            fileUrl  = awsFileUploadService.uploadFile(requestShop.getImage());
        }
        Shop shop = shopService.createShop(requestShop, username, fileUrl);
        if(shop != null) {
            return new ResponseEntity<>(new ApiResponseModel(shop, "Shop created successfully", 201, true), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ApiResponseModel(null, "Shop creation failed", 500, false), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @GetMapping
    public ResponseEntity<?> getMyShop(Authentication auth) {
        try{
            String username = auth.getName();
            Shop shop = shopService.getShop(username);
            if(shop != null) {
                return new ResponseEntity<>(new ApiResponseModel(shop, "Shop found", 200, true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponseModel(null, "Shop not found", 404, false), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ApiResponseModel(e.getMessage(), "", 500, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ShopRequest requestShop, Authentication auth) {
       try{
           String username = auth.getName();
           Shop shop = shopService.updateShop(requestShop, username);
           if(shop != null) {
               return new ResponseEntity<>(new ApiResponseModel(shop, "Shop updated successfully", 204, true), HttpStatus.NO_CONTENT);
           }
           return new ResponseEntity<>(new ApiResponseModel(null, "Shop not found", 404, false), HttpStatus.NOT_FOUND);
       } catch (RuntimeException e) {
           return new ResponseEntity<>(new ApiResponseModel(null, e.getMessage(), 500, false), HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}
