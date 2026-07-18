package com.alfin.asyncjob.api.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alfin.asyncjob.api.config.NotificationProperties;
import com.alfin.asyncjob.api.dto.request.CreateJobRequest;
import com.alfin.asyncjob.api.dto.response.CreateJobResponse;
import com.alfin.asyncjob.api.model.JobStatus;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationProperties properties;

    public NotificationService(NotificationProperties properties) {
        this.properties = properties;
    }

    public CreateJobResponse createJob(CreateJobRequest request) {
        String jobId = UUID.randomUUID().toString();

        log.info("Creating notification job");
        log.info("Recipient: {}", request.recipient());
        log.info("Subject: {}", request.subject());
        log.info("Max Retry: {}", properties.getMaxRetry());
        log.info("Sender: {}", properties.getDefaultSender());

        // throw new JobNotFoundException("123");
        // throw new DuplicateJobException("123");

        log.info("Notification job created successfully");
        return new CreateJobResponse(
                jobId,
                JobStatus.PENDING);
    }

}
