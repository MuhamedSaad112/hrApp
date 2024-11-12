package com.global.dto;

import java.util.HashSet;
import java.util.Set;

import com.global.entity.Employee;
import com.global.entity.Location;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {

	private Long id;

	@NotEmpty
	private String departmentName;

	private Location location;

	private Set<Employee> employees = new HashSet<>();

}
