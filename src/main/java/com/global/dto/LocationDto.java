package com.global.dto;

import com.global.entity.Country;

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

	@NotNull
	private Long id;

	@NotNull
	private String streetAddress;

	@NotNull
	private String postalCode;

	@NotNull
	private String city;

	@NotNull
	private String stateProvince;

	// Relationships

	@NotNull
	private Country country;

}
