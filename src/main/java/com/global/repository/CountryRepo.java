package com.global.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.global.entity.Country;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {

}
