package com.sumit.aasanorder_backend.controllers;

import com.sumit.aasanorder_backend.dto.OrderRequest;
import com.sumit.aasanorder_backend.entities.Order;
import com.sumit.aasanorder_backend.entities.Shop;
import com.sumit.aasanorder_backend.models.ApiResponseModel;
import com.sumit.aasanorder_backend.sevices.OrderService;
import com.sumit.aasanorder_backend.sevices.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/health-check")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/{shopId}/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest order, @PathVariable String shopId) {
        try{
            Order saved = orderService.createOrder(order, shopId);
            if(saved != null) {
                messagingTemplate.convertAndSend("/topic/orders/"+shopId, saved);
                return ResponseEntity.created(null).body(new ApiResponseModel(saved, "Order Placed Suucess", 201,true ));
            }
            return ResponseEntity.badRequest().body(new ApiResponseModel(null, "Order Placed Failed", 401,false ));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new ApiResponseModel(null, e.getMessage(), 501,false ));
        }
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> getShop(@PathVariable String shopId) {
        try{
            Shop shop = shopService.getShop(shopId);
            if(shop != null) {
                return ResponseEntity.ok(new ApiResponseModel(shop, "Shop Details Fetched", 200,true ));
            }
            return ResponseEntity.notFound().build();
        }catch (RuntimeException e){
            return ResponseEntity.internalServerError().body(new ApiResponseModel(null, e.getMessage(), 501,false ));
        }
    }
}
