package com.sumit.tableserve_backend.sevices;

import com.sumit.tableserve_backend.dto.ItemRequest;
import com.sumit.tableserve_backend.entities.Item;
import com.sumit.tableserve_backend.entities.Shop;
import com.sumit.tableserve_backend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AWSFileUploadService awsFileUploadService;

    // METHOD FOR CREATE NEW ITEM
    public Item save(ItemRequest item, String username) {
        try{
            Shop shop = shopService.getShop(username);
            if(shop!=null) {
                String imageUrl = awsFileUploadService.uploadFile(item.getImage());
                Item newItem = new Item().builder()
                        .itemName(item.getItemName())
                        .itemDescription(item.getItemDescription())
                        .price(item.getPrice())
                        .quantity(item.getQuantity())
                        .category(item.getCategory())
                        .subCategory(item.getSubCategory())
                        .image(imageUrl)
                        .availability(item.getAvailability())
                        .special(item.isSpecial())
                        .build();
                Item savedItem = itemRepository.save(newItem);
                shop.getItems().add(savedItem);
                shopService.updateShopByCol("items", shop.getItems(), username);
                return savedItem;
            }
            return null;

        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    //METHOD FOR GET ALL ITEMS OF A SHOP
    public List<Item> getAllItems(String username) {
        try{
            Shop shop = shopService.getShop(username);
            if(shop!=null) {
                return shop.getItems();
            }
            return Collections.emptyList();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    //GET ITEM BY ITEM_ID
    public Item getItemById(String id) {
        try{
            return  itemRepository.findByItemId(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    //FIND ALL ITEMS BY ID
    public List<Item> getAllItemsById(List<String> ids) {
        try{
            return itemRepository.findByItemIdIn(ids);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    //METHOD FOR UPDATE ITEM BY COLUMN NAME
    public void updateItemByCol(String col,Object value, String itemId) {
        try{
            Query query = new Query(Criteria.where("itemId").is(itemId));
            Update update = new Update().set(col, value);
            mongoTemplate.updateFirst(query, update, Item.class);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    //METHOD FOR DELETE ITEM
    public void deleteItemById(String itemId, String username) {
        try{
            Shop shop = shopService.getShop(username);
            if(shop!=null) {
                Item item = itemRepository.findByItemId(itemId);
                shop.getItems().remove(item);
                shopService.updateShopByCol("items", shop.getItems(), username);
                itemRepository.deleteByItemId(itemId);
            }
        }catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

}
