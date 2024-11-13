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

import com.global.dto.JobHistoryDto;
import com.global.entity.JobHistory;
import com.global.mapper.JobHistoryMapper;
import com.global.service.JobHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/jobHistory")
@RequiredArgsConstructor
@Log4j2
public class JobHistoryController {

	private final JobHistoryService jobHistoryService;
	private final JobHistoryMapper historyMapper;

	@PostMapping()
	public ResponseEntity<JobHistoryDto> save(@Valid @RequestBody JobHistoryDto dto) {
		log.debug("REST request to save JobHistory : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Job cannot already have an ID");
		}

		if (dto.getStartDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start Date is required");
		}
		if (dto.getEndDate() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End Date is required");
		}
		if (dto.getJob() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job is required");
		}
		if (dto.getDepartment() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department is required");
		}
		if (dto.getEmployee() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee is required");
		}

		if (dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start Date cannot be after End Date");
		}

		JobHistory jobHistory = historyMapper.unMap(dto);
		JobHistory entity = jobHistoryService.save(jobHistory);
		JobHistoryDto returnDto = historyMapper.map(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
	}

	@PutMapping()
	public ResponseEntity<JobHistoryDto> update(@Valid @RequestBody JobHistoryDto dto) {
		log.debug("REST request to partial update JobHistory partially : {}", dto);

		JobHistory currentJobHistory = jobHistoryService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		if (dto.getStartDate().isAfter(dto.getEndDate())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start Date cannot be after End Date");
		}

		JobHistory job = historyMapper.unMap(dto, currentJobHistory);
		JobHistory entity = jobHistoryService.update(job);
		JobHistoryDto returnDto = historyMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@GetMapping()
	public ResponseEntity<List<JobHistoryDto>> getAllJobHistories(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "startDate") String sortCol,
			@RequestParam(defaultValue = "true") Boolean isAsc) {

		log.debug("REST request to get a page of JobHistories, page: {}, size: {}, sortCol: {}, isAsc: {}", page, size,
				sortCol, isAsc);
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));

		Page<JobHistory> entities = jobHistoryService.findAll(pageable);

		if (entities.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<JobHistoryDto> dtos = historyMapper.map(entities.getContent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(entities.getTotalElements()));
		headers.add("X-Total-Pages", String.valueOf(entities.getTotalPages()));
		headers.add("X-Current-Page", String.valueOf(entities.getNumber() + 1));
		headers.add("X-Page-Size", String.valueOf(entities.getSize()));

		return ResponseEntity.ok().headers(headers).body(dtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobHistoryDto> getJobHistory(@PathVariable Long id) {
		log.debug("REST request to get JobHistory : {}", id);

		JobHistory entity = jobHistoryService.findById(id);
		if (entity != null) {
			JobHistoryDto dto = historyMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteJobHistory(@PathVariable Long id) {
		log.debug("REST request to delete JobHistory : {}", id);
		if (!jobHistoryService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			jobHistoryService.delete(id);
			return ResponseEntity.ok().build();
		}

	}

}
