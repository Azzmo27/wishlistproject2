package com.example.wishlist.controller;

import com.example.wishlist.model.Item;
import com.example.wishlist.model.User;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.ItemService;
import com.example.wishlist.service.UserService;
import com.example.wishlist.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private ItemService itemService;

    @Autowired
    UserService userService;

    private int userId;

    @GetMapping("/")
    public String frontPage () {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationSection() {
        return "registration";
    }

    @PostMapping("/newRegistration")
    public String newRegistration(@ModelAttribute User user, @RequestParam("username") String username,
                                  @RequestParam("user_password") String user_password){
        userService.createNewUser(user);
        userId = userService.getUserId(username, user_password);
        return "homePage";
    }

    @PostMapping ("/login")
    public String login(@RequestParam("username") String username, @RequestParam("user_password")
    String user_password, Model model) {
        model.addAttribute("user", userService.verifyUserLogin(username, user_password));

        if (userService.verifyUserLogin(username, user_password)) {
            userId = userService.getUserId(username, user_password);
            return "redirect:/homePage";
        }
        else
        {
            return "frontPage";
        }
    }

    @GetMapping("/homePage")
    public String homePageWishList(Model model) {
        List<WishList> wishLists = wishListService.getWishList(userId);
        model.addAttribute("wishlists", wishLists);
        return "homePage";
    }

    @GetMapping("/homePageAddItem")
    public String homePageAddItem(Model model){
        List<Item> items = itemService.fetchItems();
        model.addAttribute("items",items );
        return "homePage";
    }

    @PostMapping("/generateUniqueUrl/{wishlist_id}")
    public ResponseEntity<String> generateUniqueUrl(@PathVariable("wishlist_id") int wishlistId) {
        String uniqueUrl = wishListService.generateUniqueUrl(wishlistId); // Call generateUniqueUrl method from WishListService
        WishList wishList = wishListService.findWishlist(wishlistId).get(0); // Assuming findWishlist returns a list and we get the first item
        if (wishList != null) {
            wishList.setUniqueUrl(uniqueUrl);
            wishListService.saveOrUpdateWishList(wishList);
            return ResponseEntity.ok(uniqueUrl);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/sharedWishlist")
    public String getSharedWishlist(@RequestParam("url") String uniqueUrl, Model model) {
        WishList wishList = wishListService.getWishlistByUniqueUrl(uniqueUrl);
        if (wishList != null) {
            model.addAttribute("wishlists", List.of(wishList));
            List<Item> items = itemService.viewWishlist(wishList.getWishlistId());
            model.addAttribute("items", items);
            return "showWishlist";
        } else {
            return "wishlistNotFound";
        }
    }

    @PostMapping("/makeList")
    public String makeList(@ModelAttribute WishList wishList) {
        wishListService.createWishList(wishList, userId);
        return "redirect:/homePage";
    }

    @GetMapping("/createList")
    public String createList(){
        return "home/createList";
    }

    @GetMapping("/discoveryPage")
    public String discoveryPage(Model model) {
        List<WishList> wishlist = wishListService.discoverWishLists();
        model.addAttribute("lists", wishlist);
        return "home/discoveryPage";
    }

    @GetMapping("/viewWishlistFromSearchbar")
    public String viewWishlistFromSearchbar(@RequestParam("wishlist_id") int wishlist_id, Model model) {
        List<WishList> wishlists = wishListService.findWishlist(wishlist_id);
        model.addAttribute("wishlists", wishlists);
        List<Item> items = itemService.viewWishlist(wishlist_id);
        model.addAttribute("items", items);
        return "showWishlist";
    }

    @GetMapping("/addItem/{wishlist_id}")
    public String addItem(@PathVariable("wishlist_id") int wishlist_id, Model model) {
        model.addAttribute("wishlist_id", wishlist_id);
        return "addItem";
    }

    @PostMapping("/addItemToWishlist")
    public String addItemToWishlist(@ModelAttribute Item i) {
        itemService.addItem(i);
        return "redirect:/homePage";
    }

    @GetMapping("/deleteWishlist/{id}")
    public String deleteWishlist(@PathVariable("id") int id) {
        boolean deleted = wishListService.deleteWishlist(id);
        if (deleted) {
            return "redirect:/homePage";
        } else {
            return "redirect:/homePage";
        }
    }

    @PostMapping("/reserveItem")
    public String reserveItem(@ModelAttribute Item i){
        itemService.reserveItem(i);
        return "redirect:/homePage";
    }

    @GetMapping("/viewWishlist/{wishlist_id}")
    public String viewWishlist(@PathVariable("wishlist_id") int wishlist_id,Model model) {
        List<Item> items =itemService.viewWishlist(wishlist_id);
        model.addAttribute("items", items);
        return "viewWishList";
    }

    @GetMapping("/deleteItem/{id}")
    public String deleteItem(@PathVariable("id")int id){
        boolean deleted= itemService.deleteItem(id);
        if (deleted) {
            return "redirect:/homePage";
        }
        else {
            return "redirect:/homePage";
        }
    }

    @GetMapping("/editItems/{item_id}")
    public String editItems(@PathVariable("item_id") int id, Model model){
        model.addAttribute("item", itemService.findPersonById(id));
        return "editItem";
    }

    @PostMapping("/editWishlistItems")
    public String editWishlistItems(@ModelAttribute Item item ){
        itemService.editItem(item.getItemId(), item);
        return "redirect:/homePage";
    }
}
