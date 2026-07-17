package com.alfin.asyncjob.api.exception;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException(String jobId) {
        super("Job with id '%s' was not found.".formatted(jobId));
    }

}