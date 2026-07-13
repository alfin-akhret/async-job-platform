package com.alfin.asyncjob.api.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final EmailService emailService;

    public NotificationService(EmailService emailService) {
        this.emailService = emailService;

        System.out.println("NotificationService created");
        System.out.println("EmailService = " + emailService);
    }

}
