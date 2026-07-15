package com.alfin.asyncjob.api.dto.response;

import com.alfin.asyncjob.api.model.JobStatus;

public record CreateJobResponse(
        String jobId,
        JobStatus status) {

}
