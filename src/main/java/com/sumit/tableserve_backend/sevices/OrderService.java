package com.sumit.tableserve_backend.sevices;

import com.sumit.tableserve_backend.dto.OrderRequest;
import com.sumit.tableserve_backend.entities.Item;
import com.sumit.tableserve_backend.entities.Order;
import com.sumit.tableserve_backend.enus.OrderStatus;
import com.sumit.tableserve_backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemService itemService;

    public Order createOrder(OrderRequest orderRequest) {
        try{
            List<Item> items = itemService.getAllItemsById(orderRequest.getItems());
            Order order = new Order().builder()
                    .orderId(UUID.randomUUID().toString())
                    .shopId(orderRequest.getShopId())
                    .customerName(orderRequest.getCustomerName())
                    .customerPhone(orderRequest.getCustomerPhone())
                    .tableNumber(orderRequest.getTableNumber())
                    .items(items)
                    .totalAmount(orderRequest.getTotalAmount())
                    .orderStatus(OrderStatus.PENDING)
                    .orderDate(new Date())
                    .build();
            return orderRepository.save(order);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Order> getAllOrders(String username) {
        try{
            return orderRepository.findByShopId(username);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
