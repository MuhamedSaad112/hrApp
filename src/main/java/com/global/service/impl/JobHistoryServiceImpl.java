package com.global.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.entity.JobHistory;
import com.global.repository.JobHistoryRepo;
import com.global.service.JobHistoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class JobHistoryServiceImpl implements JobHistoryService {

	private final JobHistoryRepo historyRepo;

	@Override
	public JobHistory save(JobHistory jobHistory) {
		log.debug("Request to save jobHistory}", jobHistory);
		return historyRepo.save(jobHistory);

	}

	@Override
	public JobHistory update(JobHistory jobHistory) {
		log.debug("Request to update jobHistory}", jobHistory);
		return historyRepo.save(jobHistory);
	}

	@Override
	public Page<JobHistory> findAll(Pageable pageable) {
		log.debug("Request to Get all jobHistories");
		return historyRepo.findAll(pageable);
	}

	@Override
	public JobHistory findById(Long id) {
		log.debug("Request to Get jobHistory}", id);
		return historyRepo.findById(id).orElse(null);
	}

	@Override
	public Optional<JobHistory> getById(Long id) {
		log.debug("Request to Get JobHistory from cash: {}", id);
		return Optional.of(historyRepo.getReferenceById(id));
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete jobHistory}", id);
		historyRepo.deleteById(id);

	}

}
