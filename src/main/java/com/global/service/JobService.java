package com.global.service;

import java.util.List;

import com.global.entity.Job;

public interface JobService {

	// Save a Job.

	Job save(Job job);

	// updates a Job.

	Job update(Job job);

	// Get all the Job.

	List<Job> findAll();

	// Get the "id" Job

	Job findById(Long id);

	// Delete the "id" Job.

	void delete(Long id);
}