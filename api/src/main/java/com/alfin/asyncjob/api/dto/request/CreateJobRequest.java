package com.alfin.asyncjob.api.dto.request;

import com.alfin.asyncjob.api.model.JobType;

public record CreateJobRequest(
                JobType type,
                String recipient,
                String subject,
                String body) {
}