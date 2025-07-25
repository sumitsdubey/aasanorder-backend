package com.sumit.aasanorder_backend.entities;


import com.sumit.aasanorder_backend.enus.Availability;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private boolean special;
}
