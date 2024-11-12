package com.global.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.global.entity.Job;

public interface JobService {

	// Save a Job.

	Job save(Job job);

	// updates a Job.

	Job update(Job job);

	// Get all the Job.

	Page<Job> findAll(Pageable pageable);

	// Get the "id" Job

	Job findById(Long id);

	// Get the "id" Job from cash
	public Optional<Job> getById(Long id);

	// Delete the "id" Job.

	void delete(Long id);
}