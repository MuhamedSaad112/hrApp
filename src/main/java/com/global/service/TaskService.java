package com.global.service;

import java.util.List;
import java.util.Optional;

import com.global.entity.Task;

public interface TaskService {

	// Save a Task.

	Task save(Task task);

	// updates a Task.

	Task update(Task task);

	// Get all the Task.

	List<Task> findAll();

	// Get the "id" Task

	Task findById(Long id);

	// Get the "id" Location from cash
	public Optional<Task> getById(Long id);

	// Delete the "id" Task.

	void delete(Long id);
}