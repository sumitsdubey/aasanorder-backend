package com.sumit.tableserve_backend.sevices;

import com.mongodb.client.result.UpdateResult;
import com.sumit.tableserve_backend.dto.ShopRequest;
import com.sumit.tableserve_backend.entities.Shop;
import com.sumit.tableserve_backend.enus.Status;
import com.sumit.tableserve_backend.repositories.ShopRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ShopService {

    @Autowired
    private ShopRepositoy shopRepositoy;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Shop createShop(ShopRequest requestShop, String username, String fileUrl) {
        try{
            Shop shop = new Shop().builder()
                    .username(username)
                    .shopName(requestShop.getShopName())
                    .shopAddress(requestShop.getShopAddress())
                    .shopCity(requestShop.getShopCity())
                    .idenity(requestShop.getIdenity())
                    .phone(requestShop.getPhone())
                    .email(requestShop.getEmail())
                    .image(fileUrl)
                    .description(requestShop.getDescription())
                    .status(Status.PENDING)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Shop saved = shopRepositoy.save(shop);
            return saved;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Shop getShop(String username){
        try{
            Shop shop = shopRepositoy.findByUsername(username);
            return shop;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Shop updateShop(ShopRequest requestShop, String username) {
        try{
            Shop shop = shopRepositoy.findByUsername(username);
            if(shop != null){
                shop.setShopName(requestShop.getShopName());
                shop.setShopAddress(requestShop.getShopAddress());
                shop.setShopCity(requestShop.getShopCity());
                shop.setIdenity(requestShop.getIdenity());
                shop.setPhone(requestShop.getPhone());
                shop.setEmail(requestShop.getEmail());
//                shop.setImage(requestShop.getImage());
                shop.setDescription(requestShop.getDescription());
                shop.setUpdatedAt(LocalDateTime.now());
                return shopRepositoy.save(shop);
            }
            return null;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateShopByCol(String col, Object value, String username) {
        try{
            Query query = new Query(Criteria.where("username").is(username));
            Update update = new Update().set(col, value);
            mongoTemplate.updateFirst(query, update, Shop.class);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
