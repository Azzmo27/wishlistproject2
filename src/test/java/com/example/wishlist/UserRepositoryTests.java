
package com.example.wishlist;
import com.example.wishlist.model.User;
import com.example.wishlist.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("username");
        user.setUser_password("password");
        user.setFirst_name("name");
        user.setLast_name("lastname");
        user.setEmail("name.lastname@example.com");
        user.setBirthday("2003-01-01");

        userRepository.createUser(user);

        verify(jdbcTemplate).update(anyString(), eq(user.getUsername()), eq(user.getUserPassword()),
                eq(user.getFirstName()), eq(user.getLastName()), eq(user.getEmail()), eq(user.getBirthday()));
    }

    @Test
    public void testVerifyUserLoginSuccess() {
        String username = "testUser";
        String password = "password";
        User user = new User();

        user.setUsername(username);
        user.setUser_password(password);

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(username), eq(password)))
                .thenReturn(user);

        assertTrue(userRepository.verifyUserLogin(username, password));
    }


    @Test
    public void testVerifyUserLoginFailure() {
        String username = "testUser";
        String password = "password";

        // Simulerer, at queryForObject kaster en EmptyResultDataAccessException
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyString(), anyString()))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertFalse(userRepository.verifyUserLogin(username, password));
    }


    @Test
    public void testGetUserId() {
        String username = "testUser";
        String password = "password";
        int userId = 1;

        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyString(), anyString()))
                .thenReturn(userId);

        assertEquals(userId, userRepository.getUserId(username, password));
    }
}
