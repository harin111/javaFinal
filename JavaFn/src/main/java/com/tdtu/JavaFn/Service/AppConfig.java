package com.tdtu.JavaFn.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private AdminService adminService;

    @PostConstruct
    public void init() {
        adminService.createAdministratorAccount();
    }
}

