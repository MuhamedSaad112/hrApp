package com.global.service;

import java.util.List;
import java.util.Optional;

import com.global.entity.Region;

public interface RegionService {

	// Save a Region.

	Region save(Region region);

	// updates a Region.

	Region update(Region region);

	// Get all the Region.

	List<Region> findAll();

	// Get the "id" Region

	Region findById(Long id);

	// Get the "id" Location from cash
	public Optional<Region> getById(Long id);

	// Delete the "id" Region.

	void delete(Long id);
}