package com.sumit.tableserve_backend.dto;

import com.sumit.tableserve_backend.enus.Availability;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ItemRequest {
    private String itemName;
    private String itemDescription;
    private String price;
    private String quantity;
    private String category;
    private String subCategory;
    private MultipartFile image;
    private Availability availability;
    private boolean special;
}
