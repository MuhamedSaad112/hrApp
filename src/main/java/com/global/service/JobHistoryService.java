package com.global.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.global.entity.JobHistory;

public interface JobHistoryService {

	// Save a JobHistory.

	JobHistory save(JobHistory jobHistory);

	// updates a JobHistory.

	JobHistory update(JobHistory jobHistory);

	// Get all the JobHistory.

	Page<JobHistory> findAll(Pageable pageable);

	// Get the "id" JobHistory

	JobHistory findById(Long id);

	// Get the "id" JobHistory from cash
	public Optional<JobHistory> getById(Long id);

	// Delete the "id" JobHistory.

	void delete(Long id);
}