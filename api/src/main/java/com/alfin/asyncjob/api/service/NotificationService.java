package com.alfin.asyncjob.api.service;

import org.springframework.stereotype.Service;

import com.alfin.asyncjob.api.dto.CreateJobRequest;

@Service
public class NotificationService {

    public void createJob(CreateJobRequest request) {
        System.out.println("=== NEW JOB ===");
        System.out.println(request);
    }

}
