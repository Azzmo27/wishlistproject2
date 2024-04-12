package com.example.wishlist.repository;

import com.example.wishlist.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {
    @Autowired
    JdbcTemplate template;

    public List<Item> getItems() {
        String sql = "SELECT DISTINCT item_id, item_name, item_description, i.wishlist_id\n" +
                "FROM item i JOIN wishlist w ON i.wishlist_id = w.wishlist_id";
        RowMapper<Item> rowMapper = new BeanPropertyRowMapper<>(Item.class);
        return template.query(sql, rowMapper);
    }

    public void addItem(Item item) {
        String sql = "INSERT INTO item (item_name, item_description, wishlist_id, reserved) VALUES (?, ?, ?, ?)";
        template.update(sql, item.getItemName(), item.getItemDescription(), item.getWishlistId(), false);
    }

    public List<Item> viewItems(int wishlistId) {
        String sql = "SELECT DISTINCT item_id, item_name, item_description, i.wishlist_id\n" +
                "FROM item i\n" +
                "JOIN wishlist w ON i.wishlist_id = w.wishlist_id\n" +
                "WHERE w.wishlist_id = ?";
        RowMapper<Item> rowMapper = new BeanPropertyRowMapper<>(Item.class);
        return template.query(sql, rowMapper, wishlistId);
    }

    public void reserveItem(Item item) {
        String sql = "UPDATE item SET reserved = ? WHERE item_id = ?";
        template.update(sql, true, item.getItemId());
    }

    public Boolean deleteItem(int itemId) {
        String sql = "DELETE FROM item WHERE item_id = ?";
        return template.update(sql, itemId) > 0;
    }

    public Item itemById(int itemId) {
        String sql = "SELECT * FROM item WHERE item_id = ?";
        RowMapper<Item> rowMapper = new BeanPropertyRowMapper<>(Item.class);
        try {
            return template.queryForObject(sql, rowMapper, itemId);
        } catch (Exception e) {
            return null;
        }
    }

    public void editItem(Item item) {
        String sql = "UPDATE item SET item_name = ?, item_description = ? WHERE item_id = ?";
        template.update(sql, item.getItemName(), item.getItemDescription(), item.getItemId());
    }
}
