package com.alfin.asyncjob.api.exception;

import org.springframework.http.HttpStatus;

public class JobNotFoundException extends BusinessException {

    public JobNotFoundException(String jobId) {
        super(
                HttpStatus.NOT_FOUND,
                "Job with id '%s' was not found.".formatted(jobId));
    }

}