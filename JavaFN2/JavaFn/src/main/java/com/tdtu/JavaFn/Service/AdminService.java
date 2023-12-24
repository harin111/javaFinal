package com.tdtu.JavaFn.Service;

import com.tdtu.JavaFn.Classes.User;
import com.tdtu.JavaFn.Interface.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder; // Add this import
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Add this line

    public void createAdministratorAccount() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        userRepository.save(admin);
    }

    public void createSalespersonAccount(String fullName, String gmailAddress) {
        // Create a new salesperson
        User salesperson = new User();
        salesperson.setName(fullName);
        salesperson.setEmail(gmailAddress);

        // Set other properties as needed

        // Generate a unique token
        String token = UUID.randomUUID().toString();
        salesperson.setToken(token);

        // Set the expiry time (1 minute from now)
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(1);
        salesperson.setTokenExpiryTime(expiryTime);

        userRepository.save(salesperson);

        // Send email notification with the link
        sendAccountCreationEmail(gmailAddress, token);
    }

    private void sendAccountCreationEmail(String recipientEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Account Created");
        message.setText("Your account has been created. Click the link to log in: http://your-app-url/login?token=" + token);
        javaMailSender.send(message);
    }

    public boolean validateToken(String token) {
        User user = userRepository.findByToken(token);
        if (user != null && LocalDateTime.now().isBefore(user.getTokenExpiryTime())) {
            // Token is valid
            // Perform any additional actions if needed
            return true;
        }
        return false;
    }
}
