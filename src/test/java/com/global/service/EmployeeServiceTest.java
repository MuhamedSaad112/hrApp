package com.global.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.global.entity.Employee;
import com.global.repository.EmployeeRepo;

@SpringBootTest
public class EmployeeServiceTest {

	@Autowired
	EmployeeService employeeService;

	@MockBean
	EmployeeRepo employeeRepo;

	@Test
	void findByIdNotFound() {

		Mockito.when(employeeRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		Employee employee = employeeService.findById(12L);

		assertNull(employee);

	}

	@Test
	void findByIdFound() {
		Optional<Employee> employeeMockBean = Optional.of(new Employee(1L, "mohamed1", "saad1", "mohamed@gmail.com",
				"12345", null, 15000L, 5000L, null, null, null));

		Mockito.when(employeeRepo.findById(Mockito.anyLong())).thenReturn(employeeMockBean);

		Optional<Employee> employee = Optional.of(employeeService.findById(1L));

		assertEquals(true, employee.isPresent());
		assertEquals("mohamed1", employee.get().getFirstName());

	}

}
