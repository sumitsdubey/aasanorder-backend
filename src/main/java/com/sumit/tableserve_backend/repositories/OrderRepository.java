package com.sumit.tableserve_backend.repositories;

import com.sumit.tableserve_backend.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {

    List<Order> findByShopId(String shopId);
}
