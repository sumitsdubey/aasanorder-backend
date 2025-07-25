package com.sumit.aasanorder_backend.repositories;

import com.sumit.aasanorder_backend.entities.Shop;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepositoy extends MongoRepository<Shop, String> {

    Shop findByUsername(String username);

}
