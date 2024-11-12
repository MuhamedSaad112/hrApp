package com.global.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.entity.Employee;
import com.global.repository.EmployeeRepo;
import com.global.service.EmployeeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepo employeeRepo;

	@Override
	public Employee findByFirstNameAndLastNameOrderById(String firstName, String lastName) {
		log.debug("Request to get  Employee by FirstName And LastName : {}:{}", firstName, lastName);
		return employeeRepo.findByFirstNameAndLastNameOrderById(firstName, lastName);
	}

	@Override
	public Page<Employee> findAll(Pageable pageable) {
		log.debug("Request to Get all Employees with Sorted ");

		return employeeRepo.findAll(pageable);
	}

	@Override
	public Employee save(Employee entity) {
		log.debug("Request to save Employee : {}", entity);
		return employeeRepo.save(entity);
	}

	@Override

	public Employee update(Employee entity) {
		log.debug("Request to update Employee : {}", entity);
		return employeeRepo.save(entity);
	}

	@Override
	public Employee findById(Long id) {

		return employeeRepo.findById(id).orElse(null);
	}

	@Override
	public Optional<Employee> getById(Long id) {
		log.debug("Request to Get Employee from cash: {}", id);
		return Optional.of(employeeRepo.getReferenceById(id));
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Employee : {}", id);
		employeeRepo.deleteById(id);
	}
}
