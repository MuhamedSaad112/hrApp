package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.global.dto.CountryDto;
import com.global.entity.Country;

@Mapper
public interface CountryMapper {

	CountryDto map(Country exist);

	List<CountryDto> map(List<Country> entities);

	Country unMap(CountryDto dto);

	Country unMap(CountryDto dto, @MappingTarget Country entity);

}
