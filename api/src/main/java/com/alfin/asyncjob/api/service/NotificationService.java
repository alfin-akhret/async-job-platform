package com.alfin.asyncjob.api.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alfin.asyncjob.api.dto.request.CreateJobRequest;
import com.alfin.asyncjob.api.dto.response.CreateJobResponse;
import com.alfin.asyncjob.api.exception.JobNotFoundException;

@Service
public class NotificationService {

    public CreateJobResponse createJob(CreateJobRequest request) {
        String jobId = UUID.randomUUID().toString();

        System.out.println("=== NEW JOB ===");
        System.out.println(jobId);
        System.out.println(request);

        throw new JobNotFoundException("123");

        /**
         * return new CreateJobResponse(
         * jobId,
         * JobStatus.PENDING);
         */
    }

}
