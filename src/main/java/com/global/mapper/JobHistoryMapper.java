package com.global.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.global.dto.JobDto;
import com.global.entity.Job;

@Mapper
public interface JobHistoryMapper {

	JobDto map(Job entity);

	List<JobDto> map(List<Job> entities);

	Job unMap(JobDto dto);

	Job unMap(JobDto dto, @MappingTarget Job entity);

}
