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

import com.global.dto.JobDto;
import com.global.entity.Job;
import com.global.mapper.JobMapper;
import com.global.service.JobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/job")
@RequiredArgsConstructor
@Log4j2
public class JobController {

	private final JobService jobService;
	private final JobMapper jobMapper;

	@PostMapping()
	public ResponseEntity<JobDto> save(@RequestBody JobDto dto) {
		log.debug("REST request to save Job : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Job cannot already have an ID");
		}

		if (dto.getJobTitle() == null || dto.getJobTitle().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Name is required");
		}
		if (dto.getMaxSalary() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Max Salary is required");
		}
		if (dto.getMinSalary() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Min Salary is required");
		}
		if (dto.getEmployee() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is required");
		}
		if (dto.getTasks() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tasks is required");
		}

		Job job = jobMapper.unMap(dto);
		Job entity = jobService.save(job);
		JobDto returnDto = jobMapper.map(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
	}

	@PutMapping()
	public ResponseEntity<JobDto> update(@RequestBody JobDto dto) {
		log.debug("REST request to partial update Job partially : {}", dto);

		Job currentJob = jobService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Job job = jobMapper.unMap(dto, currentJob);
		Job entity = jobService.update(job);
		JobDto returnDto = jobMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@GetMapping()
	public ResponseEntity<List<JobDto>> getAllJobs(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "minSalary") String sortCol,
			@RequestParam(defaultValue = "true") Boolean isAsc) {

		log.debug("REST request to get a page of Job, page: {}, size: {}, sortCol: {}, isAsc: {}", page, size, sortCol,
				isAsc);
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));

		Page<Job> entities = jobService.findAll(pageable);

		if (entities.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<JobDto> dtos = jobMapper.map(entities.getContent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(entities.getTotalElements()));
		headers.add("X-Total-Pages", String.valueOf(entities.getTotalPages()));
		headers.add("X-Current-Page", String.valueOf(entities.getNumber() + 1));
		headers.add("X-Page-Size", String.valueOf(entities.getSize()));

		return ResponseEntity.ok().headers(headers).body(dtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobDto> getJob(@PathVariable Long id) {
		log.debug("REST request to get Job : {}", id);

		Job entity = jobService.findById(id);
		if (entity != null) {
			JobDto dto = jobMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
		log.debug("REST request to delete Job : {}", id);
		if (!jobService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			jobService.delete(id);
			return ResponseEntity.ok().build();
		}

	}

}
