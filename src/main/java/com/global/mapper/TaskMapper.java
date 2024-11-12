package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.global.dto.TaskDto;
import com.global.entity.Task;

@Mapper
public interface TaskMapper {

	TaskDto map(Task entity);

	List<TaskDto> map(List<Task> entities);

	Task unMap(TaskDto dto);

	Task unMap(TaskDto dto, @MappingTarget Task entity);

}
