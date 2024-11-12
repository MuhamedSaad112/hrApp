package com.global.dto;

import java.time.LocalDate;

import com.global.entity.Department;
import com.global.entity.Employee;
import com.global.entity.Job;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryDto {

	private Long id;

	@NotEmpty
	private LocalDate startDate;

	@NotEmpty
	private LocalDate endDate;

	@NotNull
	private Job job;
	
	@NotNull
	private Department department;

	@NotNull
	private Employee employee;

}
