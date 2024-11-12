package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.entity.JobHistory;

@Repository
public interface JobHistoryRepo extends JpaRepository<JobHistory, Long> {

}
