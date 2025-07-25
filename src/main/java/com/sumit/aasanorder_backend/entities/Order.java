package com.sumit.aasanorder_backend.entities;

import com.sumit.aasanorder_backend.enus.OrderStatus;
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
    private String orderTpe;
    private String tableNumber;
    private String quantity;
    @DBRef
    private List<Item> items;
    private String totalAmount;
    private OrderStatus orderStatus;
    private Date orderDate;
}
