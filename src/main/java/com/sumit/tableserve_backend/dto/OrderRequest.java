package com.sumit.tableserve_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String shopId;
    private String customerName;
    private String customerPhone;
    private String tableNumber;
    private List<String> items;
    private String totalAmount;
}
