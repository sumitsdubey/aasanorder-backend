package com.sumit.tableserve_backend.entities;


import com.sumit.tableserve_backend.enus.Availability;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("items")
public class Item {
    @Id
    private String itemId;
    @NonNull
    private String itemName;
    private String itemDescription;
    @NonNull
    private String price;
    private String quantity;
    private String category;
    private String subCategory;
    private String image;
    private Availability availability;
    private String special;
}
