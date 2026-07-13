package com.alfin.asyncjob.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alfin.asyncjob.api.service.NotificationService;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApiApplication.class, args);

        NotificationService notificationService = context.getBean(NotificationService.class);

        System.out.println(notificationService);

    }
}