package com.global.service;

import java.util.List;

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

	// Delete the "id" Region.

	void delete(Long id);
}