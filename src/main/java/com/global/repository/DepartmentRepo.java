package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.entity.Department;

@Repository
public interface DepartmentRepo extends JpaRepository<Department, Long> {

}
