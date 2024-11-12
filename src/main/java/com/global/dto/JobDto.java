package com.global.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

	private Long id;

	@NotEmpty
	private String streetAddress;

	@NotEmpty
	private String postalCode;

	@NotEmpty
	private String city;

	@NotEmpty
	private String stateProvince;

}
