package com.sumit.tableserve_backend.repositories;

import com.sumit.tableserve_backend.entities.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepositoy extends MongoRepository<Shop, String> {

    public Shop findByUsername(String username);
}
