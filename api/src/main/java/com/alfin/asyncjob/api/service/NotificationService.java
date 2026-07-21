package com.alfin.asyncjob.api.service;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alfin.asyncjob.api.config.NotificationProperties;
import com.alfin.asyncjob.api.dto.request.CreateJobRequest;
import com.alfin.asyncjob.api.dto.response.CreateJobResponse;
import com.alfin.asyncjob.api.entity.Job;
import com.alfin.asyncjob.api.model.JobStatus;
import com.alfin.asyncjob.api.repository.JobRepository;

import jakarta.transaction.Transactional;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    // inject notification properties
    private final NotificationProperties properties;

    // inject repository
    private final JobRepository jobRepository;

    public NotificationService(NotificationProperties properties, JobRepository jobRepository) {
        this.properties = properties;
        this.jobRepository = jobRepository;
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

        Job job = new Job();
        job.setId(UUID.randomUUID());
        job.setRecipient(request.recipient());
        job.setSubject(request.subject());
        job.setBody(request.body());
        job.setStatus(JobStatus.PENDING);
        job.setRetryCount(0);
        job.setCreatedAt(Instant.now());

        jobRepository.save(job);

        return new CreateJobResponse(
                jobId,
                JobStatus.PENDING);
    }

    @Transactional
    public void markAsSuccess(UUID jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        log.info("Before: {}", job.getStatus());

        job.setStatus(JobStatus.SUCCESS);

        log.info("After: {}", job.getStatus());
    }

}
