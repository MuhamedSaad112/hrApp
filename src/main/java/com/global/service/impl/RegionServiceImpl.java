package com.global.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.global.entity.Region;
import com.global.repository.RegionRepo;
import com.global.service.RegionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegionServiceImpl implements RegionService {

	private final RegionRepo regionRepo;

	@Override
	public Region save(Region region) {
		log.debug("Request to save Region : {}", region);
		return regionRepo.save(region);
	}

	@Override
	public Region update(Region region) {
		log.debug("Request to update Region : {}", region);
		return regionRepo.save(region);
	}

	@Override
	public Page<Region> findAll(Pageable pageable) {
		log.debug("Request to get all  Regions");
		return regionRepo.findAll(pageable);
	}

	@Override
	public Region findById(Long id) {
		log.debug("Request to get Region : {}", id);
		return regionRepo.findById(id).orElse(null);
	}

	@Override
	public Optional<Region> getById(Long id) {
		log.debug("Request to Get Region from cash: {}", id);
		return Optional.of(regionRepo.getReferenceById(id));
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Region : {}", id);
		regionRepo.deleteById(id);
	}

}
