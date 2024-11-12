package com.global.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.global.entity.Job;
import com.global.repository.JobRepo;
import com.global.service.JobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class JobServiceImpl implements JobService {

	private final JobRepo jobRepo;

	@Override
	public Job save(Job job) {
		log.debug("Request to save job}", job);

		return jobRepo.save(job);
	}

	@Override
	public Job update(Job job) {
		log.debug("Request to update job}", job);
		return jobRepo.save(job);
	}

	@Override
	public List<Job> findAll() {
		log.debug("Request to Get list of jobs");
		return jobRepo.findAll();
	}

	@Override
	public Job findById(Long id) {
		log.debug("Request to get job}", id);
		return jobRepo.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete job}", id);
		jobRepo.deleteById(id);
	}

}
