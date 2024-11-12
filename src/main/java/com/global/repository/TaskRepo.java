package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.entity.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

}
