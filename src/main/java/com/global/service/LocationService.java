package com.global.service;

import java.util.List;
import java.util.Optional;

import com.global.entity.Location;

public interface LocationService {

	// Save a Location.

	Location save(Location location);

	// updates a Location.

	Location update(Location location);

	// Get all the Location.

	List<Location> findAll();

	// Get the "id" Location

	Location findById(Long id);

	// Get the "id" Location from cash
	public Optional<Location> getById(Long id);

	// Delete the "id" Location.

	void delete(Long id);
}