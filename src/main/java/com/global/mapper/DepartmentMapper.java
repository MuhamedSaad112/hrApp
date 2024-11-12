package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.global.dto.DepartmentDto;
import com.global.entity.Department;

@Mapper
public interface DepartmentMapper {

	DepartmentDto map(Department entity);

	List<DepartmentDto> map(List<Department> entities);

	Department unMap(DepartmentDto dto);

	Department unMap(DepartmentDto dto, @MappingTarget Department entity);

}
