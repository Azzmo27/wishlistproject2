package com.example.wishlist.service;

import com.example.wishlist.repository.UserRepository;
import com.example.wishlist.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void createNewUser(User u){
        userRepository.createUser(u);
    }

    public Boolean verifyUserLogin(String username, String user_password) {
        return userRepository.verifyUserLogin(username, user_password);
    }

    public int getUserId(String username, String user_password){
        return userRepository.getUserId(username, user_password);
    }


}
