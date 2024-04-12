package com.example.wishlist.service;
import com.example.wishlist.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemService {
    @Autowired
    JdbcTemplate template;

    public List<Item> fetchItems(){
        String sql = "SELECT * FROM item";
        return template.query(sql, new BeanPropertyRowMapper<>(Item.class));
    }

    public void addItem(Item item) {
        String sql = "INSERT INTO item (itemName, itemDescription, wishlistId) VALUES (?, ?, ?)";
        template.update(sql, item.getItemName(), item.getItemDescription(), item.getWishlistId());
    }

    public List<Item> viewWishlist(int wishlistId) {
        String sql = "SELECT * FROM item WHERE wishlistId = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Item.class), wishlistId);
    }

    public void reserveItem(Item item){
        String sql = "UPDATE item SET reserved = 1 WHERE itemId = ?";
        template.update(sql, item.getItemId());
    }

    public Boolean deleteItem(int itemId){
        String sql = "DELETE FROM item WHERE itemId = ?";
        return template.update(sql, itemId) > 0;
    }

    public Item findPersonById(int id){
        String sql = "SELECT * FROM item WHERE itemId = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Item.class), id);
    }

    public void editItem(int itemId, Item item){
        String sql = "UPDATE item SET itemName = ?, itemDescription = ? WHERE itemId = ?";
        template.update(sql, item.getItemName(), item.getItemDescription(), itemId);
    }
}
