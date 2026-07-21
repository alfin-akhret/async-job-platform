package com.alfin.asyncjob.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alfin.asyncjob.api.entity.Job;

public interface JobRepository extends JpaRepository<Job, UUID> {

}
