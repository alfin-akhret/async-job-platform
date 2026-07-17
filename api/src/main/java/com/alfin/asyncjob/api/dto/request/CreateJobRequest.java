package com.alfin.asyncjob.api.dto.request;

import com.alfin.asyncjob.api.model.JobType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateJobRequest(
        @NotNull JobType type,

        @NotBlank @Email String recipient,

        @NotBlank String subject,

        @NotBlank String body) {
}