package com.global.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
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

}
