package com.alfin.asyncjob.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alfin.asyncjob.api.dto.request.CreateJobRequest;
import com.alfin.asyncjob.api.dto.response.CreateJobResponse;
import com.alfin.asyncjob.api.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final NotificationService notificationService;

    public JobController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateJobResponse createJob(@Valid @RequestBody CreateJobRequest request) {
        return notificationService.createJob(request);
    }

}
