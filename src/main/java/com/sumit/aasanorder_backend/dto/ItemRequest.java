package com.sumit.aasanorder_backend.dto;

import lombok.Data;

@Data
public class ItemRequest {
    private String itemName;
    private String price;
    private String category;
    private String image;
}
