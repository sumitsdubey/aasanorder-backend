package com.sumit.tableserve_backend.dto;


import lombok.Data;

@Data
public class ShopRequest {

    private String shopName;

    private String shopAddress;

    private String shopCity;

    private String idenity;

    private String phone;

    private String email;

    private String image;

    private String description;

}
