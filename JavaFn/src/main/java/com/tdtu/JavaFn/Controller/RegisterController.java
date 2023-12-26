package com.tdtu.JavaFn.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping
    public String register() {
        return "/admin/register";
    }

    @PostMapping("/createAccount")
    public String createAccount(String fullName, String gmailAddress, Model model) {
        // Split Gmail address into two parts
        String[] gmailParts = gmailAddress.split("@");
        String username = gmailParts[0];

        // Generate a random password (you can use a more secure approach)
        String password = gmailParts[0];

        // Send email with username and password
        sendEmail(gmailAddress, username, password);

        // Process the data (you can add your logic here)

        // For demonstration purposes, we'll add the parts to the model and display them in the view
        model.addAttribute("fullName", fullName);
        model.addAttribute("username", username);
        model.addAttribute("password", password);

        // Redirect to the home page or another appropriate view
        return "redirect:/products";
    }

    private void sendEmail(String to, String username, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Created");
        message.setText("Your account has been created.\n\nUsername: " + username + "\nPassword: " + password);

        javaMailSender.send(message);
    }
}
