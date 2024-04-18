package com.example.wishlist.model;

public class WishList {
    private Integer wishlistId;
    private String wishlistName;
    private String wishlistDescription;
    private int count;
    private String uniqueUrl;

    public WishList(String uniqueURL, Integer wishlistID, String wishlistName, String wishlistDescription, int count) {
        this.uniqueUrl = uniqueURL;
        this.count = count;
        this.wishlistName = wishlistName;
        this.wishlistDescription = wishlistDescription;
        this.wishlistId = wishlistID;
    }

    public WishList() {

    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
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

