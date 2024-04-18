package com.example.wishlist;
import com.example.wishlist.controller.WishListController;
import com.example.wishlist.model.Item;
import com.example.wishlist.model.User;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.ItemService;
import com.example.wishlist.service.UserService;
import com.example.wishlist.service.WishListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class WishListControllerTests {

    @Mock
    private WishListService wishListService;

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @InjectMocks
    private WishListController wishListController;

    @Mock
    private Model model;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFrontPage() {
        String result = wishListController.frontPage();
        assertEquals("login", result);
    }

    @Test
    public void testRegistration() {
        String result = wishListController.registrationSection();
        assertEquals("registration", result);
    }



    @Test
    public void testLogin_Success() {
        when(userService.verifyUserLogin("username", "password")).thenReturn(true);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/homePage");
        when(userService.getUserId("username", "password")).thenReturn(1);
        assertEquals(mav.getViewName(), wishListController.login("username", "password", model));
    }

    @Test
    public void testLogin_Failure() {
        when(userService.verifyUserLogin("username", "password")).thenReturn(false);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        assertEquals(mav.getViewName(), wishListController.login("username", "password", model));
    }

    @Test
    public void testHomePageWishList() {
        List<WishList> wishLists = new ArrayList<>();
        when(wishListService.getWishList(1)).thenReturn(wishLists);
        assertEquals("homePage", wishListController.homePageWishList(model));
    }

    @Test
    public void testHomePageAddItem() {
        List<Item> items = new ArrayList<>();
        when(itemService.fetchItems()).thenReturn(items);
        assertEquals("homePage", wishListController.homePageAddItem(model));
    }
    @Test
    public void testGenerateUniqueUrl() {
        int wishlistId = 1;
        String uniqueUrl = "generated-url";
        WishList wishList = new WishList();
        wishList.setWishlistId(wishlistId);
        when(wishListService.findWishlist(wishlistId)).thenReturn(List.of(wishList));
        when(wishListService.generateUniqueUrl(wishlistId)).thenReturn(uniqueUrl);

        ResponseEntity<String> result = wishListController.generateUniqueUrl(wishlistId);

        assertEquals(uniqueUrl, result.getBody());
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testGetSharedWishlist() {
        String uniqueUrl = "shared-url";
        WishList wishList = new WishList();
        wishList.setWishlistId(1);
        when(wishListService.getWishlistByUniqueUrl(uniqueUrl)).thenReturn(wishList);
        when(itemService.viewWishlist(wishList.getWishlistId())).thenReturn(new ArrayList<>());
        Model model = mock(Model.class);

        String view = wishListController.getSharedWishlist(uniqueUrl, model);

        assertEquals("showWishList", view);
        verify(model).addAttribute("wishlists", List.of(wishList));
        verify(model).addAttribute("items", new ArrayList<>());
    }




    @Test
    public void testSaveWishlist() {
        WishList wishList = new WishList();
        String result = wishListController.saveWishlist(wishList);
        assertEquals("redirect:/homePage", result);
        verify(wishListService).saveOrUpdateWishList(wishList);
    }

    @Test
    public void testDeleteWishlist() {
        int wishlistId = 1;
        boolean deleted = true;
        when(wishListService.deleteWishlist(wishlistId)).thenReturn(deleted);

        String result = wishListController.deleteWishlist(wishlistId);

        assertEquals("redirect:/homePage", result);
    }

    @Test
    public void testDeleteItem() {
        int itemId = 1;
        boolean deleted = true;
        when(itemService.deleteItem(itemId)).thenReturn(deleted);

        String result = wishListController.deleteItem(itemId);

        assertEquals("redirect:/homePage", result);
    }

    @Test
    public void testEditWishlistItems() {
        Item item = new Item();
        item.setItemId(1);
        String result = wishListController.editWishlistItems(item);
        assertEquals("redirect:/homePage", result);
        verify(itemService).editItem(item.getItemId(), item);
    }

}





