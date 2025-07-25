package com.sumit.aasanorder_backend.dto;

import com.sumit.aasanorder_backend.entities.Item;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String name;
    private String phone;
    private String tableNumber;
    private String oderType;
    private List<Item> items;
    private String totalAmount;
}
