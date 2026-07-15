package com.alfin.asyncjob.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
        /**
         * ConfigurableApplicationContext context =
         * SpringApplication.run(ApiApplication.class, args);
         * 
         * NotificationService notificationService =
         * context.getBean(NotificationService.class);
         * 
         * System.out.println(notificationService);
         */

        SpringApplication.run(ApiApplication.class, args);

    }
}