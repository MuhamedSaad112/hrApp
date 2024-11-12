package com.global.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.global.entity.Department;
import com.global.repository.DepartmentRepo;
import com.global.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepo departmentRepo;

	@Override
	public Department save(Department department) {
		log.debug("Request to save department : {}", department);
		return departmentRepo.save(department);
	}

	@Override
	public Department update(Department department) {
		log.debug("Request to update department :{}", department);
		return departmentRepo.save(department);
	}

	@Override
	public List<Department> findAll() {
		log.debug("Request to Get all departments");
		return departmentRepo.findAll();
	}

	@Override
	public Department findById(Long id) {
		log.debug("Request to Get department}", id);
		return departmentRepo.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete department}", id);
		departmentRepo.deleteById(id);

	}

}
