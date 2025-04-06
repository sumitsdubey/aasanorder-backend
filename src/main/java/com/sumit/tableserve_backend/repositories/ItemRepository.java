package com.sumit.tableserve_backend.repositories;

import com.sumit.tableserve_backend.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {

    public Item findByItemId(String id);

    public void deleteByItemId(String id);
}
