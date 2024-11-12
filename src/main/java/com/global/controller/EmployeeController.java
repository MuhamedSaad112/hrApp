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

import com.global.dto.EmployeeDto;
import com.global.entity.Employee;
import com.global.mapper.EmployeeMapper;
import com.global.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
@Log4j2
public class EmployeeController {

	private final EmployeeService employeeService;
	private final EmployeeMapper employeeMapper;

	@GetMapping("/filter")
	public ResponseEntity<EmployeeDto> findFirstNameAndLastName(@RequestParam String firstName,
			@RequestParam String lastName) {

		if (firstName == null || firstName.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First Name is required");
		}
		if (lastName == null || lastName.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last Name is required");
		}

		Employee entity = employeeService.findByFirstNameAndLastNameOrderById(firstName, lastName);

		if (entity == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"Employee not found with given first and last name");
		}
		EmployeeDto dto = employeeMapper.map(entity);

		return ResponseEntity.ok(dto);
	}

	@PostMapping()
	public ResponseEntity<EmployeeDto> save(@RequestBody EmployeeDto dto) {
		log.debug("REST request to save Employee : {}", dto);

		if (dto.getEmpId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new employee cannot already have an ID");
		}

		if (dto.getEmpFirstName() == null || dto.getEmpFirstName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First Name is required");
		}
		if (dto.getEmpLastName() == null || dto.getEmpLastName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last Name is required");
		}
		if (dto.getEmpEmail() == null || dto.getEmpEmail().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
		}

		Employee emp = employeeMapper.unMap(dto);
		Employee entity = employeeService.insert(emp);
		EmployeeDto returnDto = employeeMapper.map(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
	}

	@PutMapping()
	public ResponseEntity<EmployeeDto> update(@RequestBody EmployeeDto dto) {
		log.debug("REST request to partial update Employee partially : {}", dto);

		Employee currentEmp = employeeService.getByID(dto.getEmpId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));
		Employee emp = employeeMapper.unMap(dto, currentEmp);
		Employee entity = employeeService.update(emp);
		EmployeeDto returnDto = employeeMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@GetMapping()
	public ResponseEntity<List<EmployeeDto>> getAllEmployees(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "firstName") String sortCol,
			@RequestParam(defaultValue = "true") Boolean isAsc) {

		log.debug("REST request to get a page of Employees, page: {}, size: {}, sortCol: {}, isAsc: {}", page, size,
				sortCol, isAsc);
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));

		Page<Employee> entities = employeeService.findAll(pageable);

		if (entities.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<EmployeeDto> dtos = employeeMapper.map(entities.getContent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(entities.getTotalElements()));
		headers.add("X-Total-Pages", String.valueOf(entities.getTotalPages()));
		headers.add("X-Current-Page", String.valueOf(entities.getNumber() + 1));
		headers.add("X-Page-Size", String.valueOf(entities.getSize()));

		return ResponseEntity.ok().headers(headers).body(dtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
		log.debug("REST request to get Employee : {}", id);

		Employee entity = employeeService.findById(id);
		if (entity != null) {
			EmployeeDto dto = employeeMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		log.debug("REST request to delete Employee : {}", id);
		if (!employeeService.getByID(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			employeeService.delete(id);
			return ResponseEntity.ok().build();
		}

	}

}
