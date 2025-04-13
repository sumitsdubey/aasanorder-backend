package com.sumit.tableserve_backend.entities;

import com.sumit.tableserve_backend.enus.OrderStatus;
import com.sumit.tableserve_backend.enus.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("orders")
@Builder
public class Order {
    @Id
    private String orderId;
    private String shopId;
    private String customerName;
    private String customerPhone;
    private String tableNumber;
    @DBRef(db = "items")
    private List<Item> items;
    private String totalAmount;
    private OrderStatus orderStatus;
    private Date orderDate;
}
