package com.sumit.tableserve_backend.controllers;


import com.sumit.tableserve_backend.entities.Item;
import com.sumit.tableserve_backend.models.ApiResponseModel;
import com.sumit.tableserve_backend.sevices.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    public ResponseEntity<?> createItem(@RequestBody Item newItem, Authentication auth) {
        try{
            String username = auth.getName();
            Item saved = itemService.save(newItem, username);
            if(saved != null) {
                return new ResponseEntity<>(new ApiResponseModel(saved, "Item Added Success", 201, true), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(new ApiResponseModel(null, "Item Not Added", 401, false), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel(null, "Item Not Added", 500, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestParam String itemId, Authentication auth) {
        try{
            String username = auth.getName();
            itemService.deleteItemById(itemId, username);
            return new ResponseEntity<>(new ApiResponseModel(null, "Item Deleted Success", 204, true), HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel(null, "Item Not Deleted", 500, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable String itemId) {
        try{
            Item item = itemService.getItemById(itemId);
            if(item != null) {
                return new ResponseEntity<>(new ApiResponseModel(item, "Item Found", 200, true), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ApiResponseModel(null, "Item Not Found", 404, false), HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(new ApiResponseModel(null, "Item Not Found", 500, false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
