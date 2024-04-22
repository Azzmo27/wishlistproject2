package com.example.wishlist;

import com.example.wishlist.model.User;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WishlistRepositoryTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private WishListRepository wishListRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetWishList() {
        int userId = 1;
        WishList wishList = new WishList();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(Collections.singletonList(wishList));

        List<WishList> result = wishListRepository.getWishList(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(wishList, result.get(0));
    }

    @Test
    void testSaveOrUpdateWishList() {
        WishList wishList = new WishList();
        wishList.setWishlistId(1);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(Collections.singletonList(wishList));

        assertDoesNotThrow(() -> wishListRepository.saveOrUpdateWishList(wishList));

        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), anyInt()); // Tilføjede anyInt() for count
    }

    @Test
    void testCreateWishList() {
        WishList wishList = new WishList();
        wishList.setWishlistName("Test Wishlist");
        wishList.setWishlistDescription("Test Description");
        int userId = 1;

        assertDoesNotThrow(() -> wishListRepository.createWishList(wishList, new User().getUserId()));

        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), anyInt());
    }

    @Test
    void testFindWishlistById() {
        int wishlistId = 1;
        WishList wishList = new WishList();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(Collections.singletonList(wishList));

        WishList result = wishListRepository.findWishlistById(wishlistId);

        assertNotNull(result);
        assertEquals(wishlistId, result.getWishlistId());
    }

    @Test
    void testFindWishlistByUniqueUrl() {
        String uniqueUrl = "test-url";
        WishList wishList = new WishList();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyString())).thenReturn(Collections.singletonList(wishList));

        WishList result = wishListRepository.findWishlistByUniqueUrl(uniqueUrl);

        assertNotNull(result);
        assertEquals(uniqueUrl, result.getUniqueUrl());
    }

    @Test
    void testDiscoverWishLists() {
        List<WishList> wishLists = Collections.singletonList(new WishList());
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(wishLists); // Fjernet PreparedStatementSetter

        List<WishList> result = wishListRepository.discoverWishLists();

        assertNotNull(result);
        assertEquals(wishLists.size(), result.size());
    }

    @Test
    void testDeleteWishlist() {
        int wishlistId = 1;
        assertTrue(wishListRepository.deleteWishlist(wishlistId));

        verify(jdbcTemplate, times(1)).update("DELETE FROM item WHERE wishlist_id = ?", wishlistId);
        verify(jdbcTemplate, times(1)).update("DELETE FROM wishlist WHERE wishlist_id = ?", wishlistId);
    }

}

