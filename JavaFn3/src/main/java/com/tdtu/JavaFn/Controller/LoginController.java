package com.tdtu.JavaFn.Controller;

import com.tdtu.JavaFn.Classes.User;
import com.tdtu.JavaFn.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/products")
    public String login(String username, String password, Model model) {
        if (username != null) {
            User user = userService.findByUsername(username);
            if ("admin".equals(username) && "admin".equals(password) || (user != null && password.equals(user.getPassword()))) {
                return "redirect:/products?username=" + username;
            } else {
                model.addAttribute("errorMessage", "Invalid username or password");
                return "redirect:/login?error";
            }
        } else {
            model.addAttribute("errorMessage", "Invalid username or password");
            return "redirect:/login?error";
        }
    }


}
