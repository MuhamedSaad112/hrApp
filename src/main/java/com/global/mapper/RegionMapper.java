package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.global.dto.RegionDto;
import com.global.entity.Region;

@Mapper
public interface RegionMapper {

	RegionDto map(Region entity);

	List<RegionDto> map(List<Region> entities);

	Region unMap(RegionDto dto);

	Region unMap(RegionDto dto, @MappingTarget Region entity);

}
