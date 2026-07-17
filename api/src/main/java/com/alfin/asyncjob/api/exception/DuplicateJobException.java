package com.alfin.asyncjob.api.exception;

import org.springframework.http.HttpStatus;

public class DuplicateJobException extends BusinessException {

    public DuplicateJobException(String jobId) {
        super(
                HttpStatus.BAD_REQUEST,
                "Duplicate job, jobID: '%s'".formatted(jobId));

    }
}
