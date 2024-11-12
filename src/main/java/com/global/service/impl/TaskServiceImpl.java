package com.global.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.global.entity.Task;
import com.global.repository.TaskRepo;
import com.global.service.TaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskServiceImpl implements TaskService {

	private final TaskRepo taskRepo;

	@Override
	public Task save(Task task) {
		log.debug("Request to save Task : {}", task);
		return taskRepo.save(task);
	}

	@Override
	public Task update(Task task) {
		log.debug("Request to update Task : {}", task);
		return taskRepo.save(task);
	}

	@Override
	public List<Task> findAll() {
		log.debug("Request to get all Task ");
		return taskRepo.findAll();
	}

	@Override
	public Task findById(Long id) {
		log.debug("Request to get Task : {}", id);
		return taskRepo.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Task : {}", id);
		taskRepo.deleteById(id);

	}

}
