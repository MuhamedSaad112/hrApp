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

import com.global.dto.DepartmentDto;
import com.global.entity.Department;
import com.global.mapper.DepartmentMapper;
import com.global.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/department")
@RequiredArgsConstructor
@Log4j2
public class DepartmenController {

	private final DepartmentService departmentService;
	private final DepartmentMapper departmentMapper;

	@PostMapping()
	public ResponseEntity<DepartmentDto> save(@RequestBody DepartmentDto dto) {
		log.debug("REST request to save Department : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Department cannot already have an ID");
		}

		if (dto.getDepartmentName() == null || dto.getDepartmentName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department Name is required");
		}
		if (dto.getLocation() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region is Location");
		}
		if (dto.getEmployees() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region is Location");
		}

		Department Department = departmentMapper.unMap(dto);
		Department entity = departmentService.save(Department);
		DepartmentDto returnDto = departmentMapper.map(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
	}

	@PutMapping()
	public ResponseEntity<DepartmentDto> update(@RequestBody DepartmentDto dto) {
		log.debug("REST request to partial update Department partially : {}", dto);

		Department currentDepartment = departmentService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Department Department = departmentMapper.unMap(dto, currentDepartment);
		Department entity = departmentService.update(Department);
		DepartmentDto returnDto = departmentMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@GetMapping()
	public ResponseEntity<List<DepartmentDto>> getAllCountries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "firstName") String sortCol,
			@RequestParam(defaultValue = "true") Boolean isAsc) {

		log.debug("REST request to get a page of Department, page: {}, size: {}, sortCol: {}, isAsc: {}", page, size,
				sortCol, isAsc);
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));

		Page<Department> entities = departmentService.findAll(pageable);

		if (entities.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<DepartmentDto> dtos = departmentMapper.map(entities.getContent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(entities.getTotalElements()));
		headers.add("X-Total-Pages", String.valueOf(entities.getTotalPages()));
		headers.add("X-Current-Page", String.valueOf(entities.getNumber() + 1));
		headers.add("X-Page-Size", String.valueOf(entities.getSize()));

		return ResponseEntity.ok().headers(headers).body(dtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DepartmentDto> getEmployee(@PathVariable Long id) {
		log.debug("REST request to get Department : {}", id);

		Department entity = departmentService.findById(id);
		if (entity != null) {
			DepartmentDto dto = departmentMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		log.debug("REST request to delete Department : {}", id);
		if (!departmentService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			departmentService.delete(id);
			return ResponseEntity.ok().build();
		}

	}

}
