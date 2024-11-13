package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.entity.Authority;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, String> {
}
