package com.global.service;

import java.util.List;

import com.global.entity.Country;

public interface CountryService {

	// Save a country.

	Country save(Country country);

	// Partially updates a country.

	Country update(Country country);

	// Get all the countries.

	List<Country> findAll();

	// Get the "id" country

	Country findById(Long id);

	// Delete the "id" country.

	void delete(Long id);
}
