package com.global.dto;

import com.global.entity.Region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {


	private Long id;


	private String countryName;


	private Region region;
	
}
