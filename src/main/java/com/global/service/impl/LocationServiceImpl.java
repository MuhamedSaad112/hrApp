package com.global.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.entity.Location;
import com.global.repository.LocationRepo;
import com.global.service.LocationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class LocationServiceImpl implements LocationService {

	private final LocationRepo locationRepo;

	@Override
	public Location save(Location location) {
		log.debug("Request to save location}", location);
		return locationRepo.save(location);
	}

	@Override
	public Location update(Location location) {
		log.debug("Request to update location}", location);
		return locationRepo.save(location);
	}

	@Override
	public Page<Location> findAll(Pageable pageable) {
		log.debug("Request to Get list of locations");
		return locationRepo.findAll(pageable);
	}

	@Override
	public Location findById(Long id) {
		log.debug("Request to Get  location}", id);
		return locationRepo.findById(id).orElse(null);
	}

	@Override
	public Optional<Location> getById(Long id) {
		log.debug("Request to Get Location from cash: {}", id);
		return Optional.of(locationRepo.getReferenceById(id));
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete location}", id);
		locationRepo.deleteById(id);
	}

}
