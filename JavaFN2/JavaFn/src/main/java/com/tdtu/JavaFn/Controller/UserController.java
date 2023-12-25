package com.tdtu.JavaFn.Controller;

import com.tdtu.JavaFn.Classes.User;
import com.tdtu.JavaFn.Interface.UserRepository;
import com.tdtu.JavaFn.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Random;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/createAccount")
    public String createAccount(@RequestParam("fullName") String fullName,
                                @RequestParam("gmailAddress") String gmailAddress) {

        // Extract username and password from the email address
        String[] emailParts = gmailAddress.split("@");
        String username = emailParts[0];
        String password = emailParts[0];

        // Generate a random 9-digit token
        String token = String.format("%09d", new Random().nextInt(1000000000));

        // Calculate token expiry time (1 minute from the current time)
        LocalDateTime tokenExpiryTime = LocalDateTime.now().plusMinutes(1);

        // Create a new user and save it to the database
        User newUser = new User(fullName, gmailAddress, username, password, token, tokenExpiryTime);
        userRepository.save(newUser);

        String verificationLink = "http://localhost:8080/verifyEmail?token=" + token;
        emailSenderService.sendEmail(gmailAddress, "Email Verification", "Your account has been created. Click the following link to verify your email: " + verificationLink);

        return "redirect:/login";
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        // Find the user by the token
        User user = userRepository.findByToken(token);

        // Verify the email and update the user in the database
        if (user != null) {
            user.setEmailVerified(true);
            userRepository.save(user);
            return "redirect:/login"; // Redirect to the login page or any other page as needed
        } else {
            // Handle invalid token or user not found
            return "redirect:/error";
        }
    }
}
