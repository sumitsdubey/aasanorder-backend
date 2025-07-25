package com.sumit.aasanorder_backend.controllers;

import com.sumit.aasanorder_backend.entities.Order;
import com.sumit.aasanorder_backend.models.ApiResponseModel;
import com.sumit.aasanorder_backend.sevices.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {


    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;


    @GetMapping
    public ResponseEntity<?> getOrders(Authentication auth) {
        try{
            String username = auth.getName();
            List<Order> allOrders = orderService.getAllOrders(username);
            if(allOrders != null && !allOrders.isEmpty()){
                return ResponseEntity.ok(new ApiResponseModel(allOrders, "Orders Found", 200, true));
            }
            return new ResponseEntity<>(new ApiResponseModel(null, "Orders Not Found", 401, false), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(new ApiResponseModel(null, "Internal Server Error", 501, false));
        }
    }
}
