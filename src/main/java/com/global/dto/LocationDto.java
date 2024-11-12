package com.global.dto;

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
public class LocationDto {

	private Long id;

	@NotEmpty
	private String jobTitle;

	@NotNull
	private Long minSalary;

	@NotNull
	private Long maxSalary;

}
