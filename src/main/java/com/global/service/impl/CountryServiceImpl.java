package com.global.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.global.entity.Country;
import com.global.repository.CountryRepo;
import com.global.service.CountryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CountryServiceImpl implements CountryService {

	private final CountryRepo countryRepo;

	@Override
	public Country save(Country country) {
		log.debug("Request to save Country : {}", country);
		return countryRepo.save(country);
	}

	@Override
	public Country update(Country country) {
		log.debug("Request to  update Country : {}", country);

		return countryRepo.save(country);
	}

	@Override
	public List<Country> findAll() {
		log.debug("Request to get all Countries");
		return countryRepo.findAll();
	}

	@Override
	public Country findById(Long id) {
		log.debug("Request to get Country : {}", id);
		return countryRepo.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Country : {}", id);
		countryRepo.deleteById(id);

	}

}
