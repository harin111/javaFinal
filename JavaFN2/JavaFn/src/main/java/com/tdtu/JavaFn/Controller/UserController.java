package com.tdtu.JavaFn.Controller;

import com.tdtu.JavaFn.Classes.User;
import com.tdtu.JavaFn.Service.UserService;
import com.tdtu.JavaFn.Service.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Add this import
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder; // Add this line

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Replace this with your actual authentication logic
        User user = userService.findByUsername(username);

        if (user != null && (password.equals(user.getPassword()))) {
            // Successful login
            model.addAttribute("username", username);
            return "redirect:/products"; // Redirect to the product page or any other page
        } else {
            // Failed login
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
