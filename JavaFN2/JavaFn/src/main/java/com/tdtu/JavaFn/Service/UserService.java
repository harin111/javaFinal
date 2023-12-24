package com.tdtu.JavaFn.Service;

import com.tdtu.JavaFn.Classes.User;
import com.tdtu.JavaFn.Interface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        System.out.println("Searching for user with username: " + username);
        // Your existing logic to retrieve the user
        User user = userRepository.findByUsername(username);
        System.out.println("Found user: " + user);
        return user;
    }

}
