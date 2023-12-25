package com.tdtu.JavaFn.Controller;
import com.tdtu.JavaFn.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangePasswordController {

    private UserService userService;

    @Autowired
    public ChangePasswordController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(@RequestParam(name = "username") String username, Model model) {
        if (!model.containsAttribute("username")) {
            model.addAttribute("username", username);
        }
        return "changePassword";
    }




    @PostMapping("/change-password")
    public String changePassword(@RequestParam(name = "username") String username,
                                 @RequestParam(name = "newPassword") String newPassword,
                                 Model model) {
        // Update the user's password and set the passwordChanged flag to true
        System.out.println("ChangePasswordController: " + username);
        userService.changePassword(username, newPassword);

        // Redirect to the products page after changing the password
        return "redirect:/products?username=" + username;
    }
}

