package com.tdtu.JavaFn.Interface;
import com.tdtu.JavaFn.Classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


public interface UserService extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    default void changePassword(String username, String newPassword) {
        User user = findByUsername(username);
        System.out.println(username);
        if (user != null) {
            // Update the user's password and set the passwordChanged flag to true
            user.setPassword(newPassword);
            user.setPasswordChanged(true);
            save(user);
        } else {
            // Handle the case where the user is not found
            throw new IllegalArgumentException("User not found");
        }
    }
}


