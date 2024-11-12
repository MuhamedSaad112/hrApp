package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.entity.Region;

@Repository
public interface RegionRepo extends JpaRepository<Region, Long> {

}
