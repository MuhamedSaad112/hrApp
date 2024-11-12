package com.global.service;

import java.util.List;

import com.global.entity.JobHistory;

public interface JobHistoryService {

	// Save a JobHistory.

	JobHistory save(JobHistory jobHistory);

	// updates a JobHistory.

	JobHistory update(JobHistory jobHistory);

	// Get all the JobHistory.

	List<JobHistory> findAll();

	// Get the "id" JobHistory

	JobHistory findById(Long id);

	// Delete the "id" JobHistory.

	void delete(Long id);
}