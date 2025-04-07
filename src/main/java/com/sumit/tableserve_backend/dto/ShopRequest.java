package com.sumit.tableserve_backend.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ShopRequest {

    private String shopName;

    private String shopAddress;

    private String shopCity;

    private String idenity;

    private String phone;

    private String email;

    private MultipartFile image;

    private String description;

}
