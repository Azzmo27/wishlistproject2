package com.example.wishlist.service;

import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    @Autowired
    WishListRepository wishListRepository;

    public List<WishList> getWishList(int userId){
        return wishListRepository.getWishList(userId);
    }

    public void createWishList(WishList wishList, int userId){
        wishListRepository.createWishList(wishList, userId);
    }

    public Boolean deleteWishlist(int id){
        return wishListRepository.deleteWishlist(id);
    }

    public List<WishList> discoverWishLists(){
        return wishListRepository.discoverWishLists();
    }

    public List<WishList> findWishlist(int wishlistId) {
        return wishListRepository.findWishlist(wishlistId);
    }

    public String generateUniqueUrl(int wishlistId) {
        String uniqueUrl = generateUniqueUrlInternal(); // Call the internal method to generate a unique URL
        WishList wishList = wishListRepository.findWishlistById(wishlistId);
        if (wishList != null) {
            wishList.setUniqueUrl(uniqueUrl);
            wishListRepository.saveOrUpdateWishList(wishList);
            return uniqueUrl;
        } else {
            return null;
        }
    }

    public WishList getWishlistByUniqueUrl(String uniqueUrl) {
        return wishListRepository.findWishlistByUniqueUrl(uniqueUrl);
    }

    public String generateUniqueUrlInternal() {

        return "unique-url";
    }

    public void saveOrUpdateWishList(WishList wishList) {
        wishListRepository.saveOrUpdateWishList(wishList);
    }

}
