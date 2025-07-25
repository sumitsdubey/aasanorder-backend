package com.sumit.aasanorder_backend.sevices;

import com.sumit.aasanorder_backend.dto.OrderRequest;
import com.sumit.aasanorder_backend.entities.Order;
import com.sumit.aasanorder_backend.entities.Shop;
import com.sumit.aasanorder_backend.enus.OrderStatus;
import com.sumit.aasanorder_backend.repositories.OrderRepository;
import com.sumit.aasanorder_backend.repositories.ShopRepositoy;
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

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopRepositoy shopRepositoy;

    public Order createOrder(OrderRequest orderRequest, String shopId) {
        try{
            Shop shop = shopService.getShop(shopId);
            Order order = new Order().builder()
                    .orderId(UUID.randomUUID().toString())
                    .shopId(shopId)
                    .customerName(orderRequest.getName())
                    .customerPhone(orderRequest.getPhone())
                    .tableNumber(orderRequest.getTableNumber())
                    .orderTpe(orderRequest.getOderType())
                    .items(orderRequest.getItems())
                    .totalAmount(orderRequest.getTotalAmount())
                    .orderStatus(OrderStatus.PENDING)
                    .orderDate(new Date())
                    .build();
            Order saved = orderRepository.save(order);
            shop.getOrders().add(saved);
            shopRepositoy.save(shop);
            return saved;
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
