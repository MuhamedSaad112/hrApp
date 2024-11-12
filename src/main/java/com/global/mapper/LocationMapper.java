package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.global.dto.LocationDto;
import com.global.entity.Location;

@Mapper
public interface LocationMapper {

	LocationDto map(Location entity);

	List<LocationDto> map(List<Location> entities);

	Location unMap(LocationDto dto);

	Location unMap(LocationDto dto, @MappingTarget Location entity);

}
