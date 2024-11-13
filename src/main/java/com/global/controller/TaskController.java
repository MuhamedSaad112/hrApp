package com.global.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.global.dto.TaskDto;
import com.global.entity.Task;
import com.global.mapper.TaskMapper;
import com.global.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Log4j2
public class TaskController {

	private final TaskService taskService;
	private final TaskMapper taskMapper;

	@PostMapping()
	public ResponseEntity<TaskDto> save(@Valid @RequestBody TaskDto dto) {
		log.debug("REST request to save Task : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Task cannot already have an ID");
		}

		if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Title is required");
		}

		if (dto.getDescription() == null || dto.getDescription().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Description is required");
		}

		if (dto.getJobs() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Jobs is required");
		}

		Task Task = taskMapper.unMap(dto);
		Task entity = taskService.save(Task);
		TaskDto returnDto = taskMapper.map(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
	}

	@PutMapping()
	public ResponseEntity<TaskDto> update(@Valid @RequestBody TaskDto dto) {
		log.debug("REST request to partial update Task partially : {}", dto);

		Task currentTask = taskService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Task Task = taskMapper.unMap(dto, currentTask);
		Task entity = taskService.update(Task);
		TaskDto returnDto = taskMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@GetMapping()
	public ResponseEntity<List<TaskDto>> getAllTasks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "firstName") String sortCol,
			@RequestParam(defaultValue = "true") Boolean isAsc) {

		log.debug("REST request to get a page of Task, page: {}, size: {}, sortCol: {}, isAsc: {}", page, size, sortCol,
				isAsc);
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));

		Page<Task> entities = taskService.findAll(pageable);

		if (entities.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<TaskDto> dtos = taskMapper.map(entities.getContent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(entities.getTotalElements()));
		headers.add("X-Total-Pages", String.valueOf(entities.getTotalPages()));
		headers.add("X-Current-Page", String.valueOf(entities.getNumber() + 1));
		headers.add("X-Page-Size", String.valueOf(entities.getSize()));

		return ResponseEntity.ok().headers(headers).body(dtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
		log.debug("REST request to get Task : {}", id);

		Task entity = taskService.findById(id);
		if (entity != null) {
			TaskDto dto = taskMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
		log.debug("REST request to delete Task : {}", id);
		if (!taskService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			taskService.delete(id);
			return ResponseEntity.ok().build();
		}

	}

}
