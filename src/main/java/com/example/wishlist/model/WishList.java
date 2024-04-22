package com.example.wishlist.model;
public class WishList {
    private Integer wishlistId;
    private String wishlistName;
    private String wishlistDescription;
    private int count;
    private String uniqueUrl;

    public WishList(String uniqueUrl, Integer wishlistId, String wishlistName, String wishlistDescription, int count) {
        this.uniqueUrl = uniqueUrl;
        this.wishlistId = wishlistId;
        this.wishlistName = wishlistName;
        this.wishlistDescription = wishlistDescription;
        this.count = count;
    }

    public WishList() {

    }

    public Integer getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public String getWishlistDescription() {
        return wishlistDescription;
    }

    public void setWishlistDescription(String wishlistDescription) {
        this.wishlistDescription = wishlistDescription;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getUniqueUrl() {
        return uniqueUrl;
    }

    public void setUniqueUrl(String uniqueUrl) {
        this.uniqueUrl = uniqueUrl;
    }
}
