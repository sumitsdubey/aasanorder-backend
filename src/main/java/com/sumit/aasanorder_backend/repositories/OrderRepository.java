package com.sumit.aasanorder_backend.repositories;

import com.sumit.aasanorder_backend.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {

    List<Order> findByShopId(String shopId);
}
