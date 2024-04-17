package com.example.wishlist.model;

public class Item {
    private String itemName;
    private int itemId;
    private String itemDescription;
    private int wishlistId;
    private boolean reserved;

    public Item() {
    }

    public Item(String itemName, String itemDescription, int wishlistId) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.wishlistId = wishlistId;
    }

    public String getItemName() {
        return itemName;
    }


    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemId=" + itemId +
                ", itemDescription='" + itemDescription + '\'' +
                ", wishlistId=" + wishlistId +
                ", reserved=" + reserved +
                '}';
    }
}
