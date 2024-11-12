package com.global.service;

import java.util.List;

import com.global.entity.Department;

public interface DepartmentService {

	// Save a Department.

	Department save(Department department);

	// Partially updates a Department.

	Department update(Department department);

	// Get all the department.

	List<Department> findAll();

	// Get the "id" Department

	Department findById(Long id);

	// Delete the "id" Department.

	void delete(Long id);
}