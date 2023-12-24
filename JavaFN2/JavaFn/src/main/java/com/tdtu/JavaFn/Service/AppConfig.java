package com.tdtu.JavaFn.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class AppConfig {

    @Autowired
    private AdminService adminService;

    @PostConstruct
    public void init() {
        adminService.createAdministratorAccount();
    }
}

