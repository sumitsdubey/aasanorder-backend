package com.sumit.tableserve_backend.repositories;

import com.sumit.tableserve_backend.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {

    public Item findByItemId(String id);

    public void deleteByItemId(String id);

    public List<Item> findByItemIdIn(List<String> id);


}
