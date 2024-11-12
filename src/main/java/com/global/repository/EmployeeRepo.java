package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	Employee findByFirstNameAndLastNameOrderById(String firstName, String lastName);

}
