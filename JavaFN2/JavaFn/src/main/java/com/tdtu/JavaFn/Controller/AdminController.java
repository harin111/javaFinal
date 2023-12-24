package com.tdtu.JavaFn.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @GetMapping("/admin/change-password")
    public String showChangePasswordForm() {
        return "change-password";
    }

    @PostMapping("/admin/change-password")
    public String changePassword(@RequestParam String newPassword, Model model) {
        // Update the administrator's password in the database
        // Add logic to save the new password securely (e.g., using a password encoder)
        model.addAttribute("message", "Password changed successfully");
        return "change-password";
    }
}

