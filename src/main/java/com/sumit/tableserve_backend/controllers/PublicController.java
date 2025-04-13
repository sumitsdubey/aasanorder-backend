package com.sumit.tableserve_backend.controllers;

import com.sumit.tableserve_backend.dto.OrderRequest;
import com.sumit.tableserve_backend.entities.Order;
import com.sumit.tableserve_backend.models.ApiResponseModel;
import com.sumit.tableserve_backend.repositories.OrderRepository;
import com.sumit.tableserve_backend.sevices.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/health-check")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/create-order/{username}")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest order, @DestinationVariable String username) {
        try{
            Order saved = orderService.createOrder(order);
            if(saved != null) {
                messagingTemplate.convertAndSend("/topic/orders/{username}", saved);
                return ResponseEntity.created(null).body(new ApiResponseModel(saved, "Order Placed Suucess", 201,true ));
            }
            return ResponseEntity.badRequest().body(new ApiResponseModel(null, "Order Placed Failed", 401,false ));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new ApiResponseModel(null, e.getMessage(), 501,false ));
        }
    }
}
