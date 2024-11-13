package com.global.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.entity.Job;

@Repository
public interface JobRepo extends JobRepositoryWithBagRelationships, JpaRepository<Job, Long> {

	default Optional<Job> findOneWithEagerRelationships(Long id) {
		return this.fetchBagRelationships(this.findById(id));
	}

	default List<Job> findAllWithEagerRelationships() {
		return this.fetchBagRelationships(this.findAll());
	}

	default Page<Job> findAllWithEagerRelationships(Pageable pageable) {
		return this.fetchBagRelationships(this.findAll(pageable));
	}
}
