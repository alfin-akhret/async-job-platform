package com.alfin.asyncjob.api.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alfin.asyncjob.api.dto.request.CreateJobRequest;
import com.alfin.asyncjob.api.dto.response.CreateJobResponse;
import com.alfin.asyncjob.api.model.JobStatus;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public CreateJobResponse createJob(CreateJobRequest request) {
        String jobId = UUID.randomUUID().toString();

        log.info("Creating notification job");
        log.info("Recipient: {}", request.recipient());
        log.info("Subject: {}", request.subject());

        // throw new JobNotFoundException("123");
        // throw new DuplicateJobException("123");

        log.info("Notification job created successfully");
        return new CreateJobResponse(
                jobId,
                JobStatus.PENDING);
    }

}
