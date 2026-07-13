package com.alfin.asyncjob.api.dto;

public record CreateJobRequest(
        String type,
        String recipient,
        String subject,
        String body) {
}