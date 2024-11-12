package com.global.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.global.entity.Employee;

public interface EmployeeService {

	// Get the "id" Employee from cash
	public Optional<Employee> getByID(Long id);

	// Get the "FirstName And LastName" Employee
	Employee findByFirstNameAndLastNameOrderById(String firstName, String lastName);

	// Save a Employee.

	Employee insert(Employee Employee);

	// updates a Employee.

	Employee update(Employee Employee);

	// Get all the Employee.

	Page<Employee> findAll(Pageable pageable);

	// Get the "id" Employee

	Employee findById(Long id);

	// Delete the "id" Employee.

	void delete(Long id);

}
