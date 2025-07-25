package com.sumit.aasanorder_backend.repositories;

import com.sumit.aasanorder_backend.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    Item findByItemId(String id);

    void deleteByItemId(String id);

    List<Item> findByItemIdIn(List<String> id);


}
