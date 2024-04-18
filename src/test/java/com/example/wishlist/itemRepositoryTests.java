package com.example.wishlist;

import com.example.wishlist.model.Item;
import com.example.wishlist.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class itemRepositoryTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ItemRepository itemRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetItems() {
        List<Item> expectedItems = new ArrayList<>();


        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(expectedItems);

        List<Item> actualItems = itemRepository.getItems();

        assertEquals(expectedItems, actualItems);
    }

    @Test
    public void testAddItem() {
        Item item = new Item();


        itemRepository.addItem(item);

        verify(jdbcTemplate).update(anyString(), any(), any(), any(), anyBoolean());
    }

    @Test
    public void testViewItems() {
        int wishlistId = 1;
        List<Item> expectedItems = new ArrayList<>();


        when(jdbcTemplate.query(anyString(), any(RowMapper.class), anyInt())).thenReturn(expectedItems);

        List<Item> actualItems = itemRepository.viewItems(wishlistId);

        assertEquals(expectedItems, actualItems);
    }

    @Test
    public void testReserveItem() {
        Item item = new Item();
        item.setItemId(1);

        itemRepository.reserveItem(item);

        verify(jdbcTemplate).update(anyString(), anyBoolean(), anyInt());
    }

    @Test
    public void testDeleteItem() {
        int itemId = 1;

        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);

        boolean result = itemRepository.deleteItem(itemId);

        assertEquals(true, result);
    }

    @Test
    public void testDeleteItem_NotFound() {
        int itemId = 1;

        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(0);

        boolean result = itemRepository.deleteItem(itemId);

        assertEquals(false, result);
    }

    @Test
    public void testItemById() {
        int itemId = 1;
        Item expectedItem = new Item();

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(expectedItem);

        Item actualItem = itemRepository.itemById(itemId);

        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void testItemById_NotFound() {
        int itemId = 1;

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(null);

        Item actualItem = itemRepository.itemById(itemId);

        assertNull(actualItem);
    }

    @Test
    public void testEditItem() {
        Item item = new Item();

        itemRepository.editItem(item);

        verify(jdbcTemplate).update(anyString(), any(), any(), anyInt());
    }
}
