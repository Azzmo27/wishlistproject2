package com.example.wishlist.repository;

import com.example.wishlist.model.WishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class WishListRepository {
    @Autowired
    private final JdbcTemplate template;

    public WishListRepository(JdbcTemplate template) {
        this.template = template;
    }
    public List<WishList> getWishList(int userId) {
        String sql = "SELECT * FROM wishlist WHERE user_id = ?";
        RowMapper<WishList> rowMapper = new BeanPropertyRowMapper<>(WishList.class);
        List<WishList> wishLists = template.query(sql, rowMapper, userId);

        for (WishList w : wishLists) {
            int id = w.getWishlistId();
            String sql2 = "SELECT COUNT(item_id) FROM item WHERE wishlist_id = ?";
            int count = template.queryForObject(sql2, Integer.class, id);
            w.setCount(count);
        }
        return wishLists;
    }
    @Transactional
    public void saveOrUpdateWishList(WishList wishList) {
        int wishlistId = wishList.getWishlistId();
        WishList existingWishList = findWishlistById(wishlistId);
        if (existingWishList != null) {

            String updateSql = "UPDATE wishlist SET wishlist_name = ?, wishlist_description = ?, unique_url = ? WHERE wishlist_id = ?";
            template.update(updateSql, wishList.getWishlistName(), wishList.getWishlistDescription(), wishList.getUniqueUrl(), wishlistId); 
        } else {

            throw new IllegalArgumentException("Wishlist with ID " + wishlistId + " does not exist.");
        }
    }
    public WishList findWishlistById(int wishlistId) {
        String sql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        RowMapper<WishList> rowMapper = new BeanPropertyRowMapper<>(WishList.class);
        List<WishList> wishLists = template.query(sql, rowMapper, wishlistId);
        if (wishLists.isEmpty()) {
            return null;
        } else {
            return wishLists.get(0);
        }
    }
    public void createWishList(WishList w, int user) {
        String sql = "INSERT INTO wishlist (wishlist_name, wishlist_description, user_id) VALUES (?, ?, ?)";
        template.update(sql, w.getWishlistName(), w.getWishlistDescription(), user);
    }

    public WishList findWishlistByUniqueUrl(String uniqueUrl) {
        String sql = "SELECT * FROM wishlist WHERE unique_url = ?";
        RowMapper<WishList> rowMapper = new BeanPropertyRowMapper<>(WishList.class);
        List<WishList> wishLists = template.query(sql, rowMapper, uniqueUrl);
        if (wishLists.isEmpty()) {
            return null;
        } else {
            return wishLists.get(0);
        }
    }
    public List<WishList> discoverWishLists() {
        String sql = "SELECT * FROM wishlist";
        RowMapper<WishList> rowMapper = new BeanPropertyRowMapper<>(WishList.class);
        return template.query(sql, rowMapper);
    }
    public List<WishList> findWishlist(int wishlistId) {
        String sql = "SELECT w.wishlist_name, i.item_name, i.item_description, w.wishlist_id " +
                "FROM wishlist w LEFT JOIN item i ON w.wishlist_id = i.wishlist_id " +
                "WHERE w.wishlist_id = ?";
        RowMapper<WishList> rowMapper = new BeanPropertyRowMapper<>(WishList.class);
        return template.query(sql, rowMapper, wishlistId);
    }
    public Boolean deleteWishlist(int id) {
        String deleteItemsSql = "DELETE FROM item WHERE wishlist_id = ?";
        int itemsDeleted = template.update(deleteItemsSql, id);

        String deleteWishlistSql = "DELETE FROM wishlist WHERE wishlist_id = ?";
        int wishlistDeleted = template.update(deleteWishlistSql, id);

        return itemsDeleted > 0 && wishlistDeleted > 0;
    }

}
