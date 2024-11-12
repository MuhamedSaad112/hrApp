package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.global.dto.JobHistoryDto;
import com.global.entity.JobHistory;

@Mapper
public interface JobMapper {

	JobHistoryDto map(JobHistory entity);

	List<JobHistoryDto> map(List<JobHistory> entities);

	JobHistory unMap(JobHistoryDto dto);

	JobHistory unMap(JobHistoryDto dto, @MappingTarget JobHistory entity);

}
