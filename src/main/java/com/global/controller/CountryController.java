package com.global.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.global.dto.CountryDto;
import com.global.entity.Country;
import com.global.mapper.CountryMapper;
import com.global.service.CountryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/country")
@RequiredArgsConstructor
@Log4j2
public class CountryController {

	private final CountryService countryService;
	private final CountryMapper countryMapper;

	@PostMapping()
	public ResponseEntity<CountryDto> save(@RequestBody CountryDto dto) {
		log.debug("REST request to save Country : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Country cannot already have an ID");
		}

		if (dto.getCountryName() == null || dto.getCountryName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country Name is required");
		}
		if (dto.getRegion() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region is required");
		}

		Country country = countryMapper.unMap(dto);
		Country entity = countryService.save(country);
		CountryDto returnDto = countryMapper.map(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
	}

	@PutMapping()
	public ResponseEntity<CountryDto> update(@RequestBody CountryDto dto) {
		log.debug("REST request to partial update Country partially : {}", dto);

		Country currentCountry = countryService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Country country = countryMapper.unMap(dto, currentCountry);
		Country entity = countryService.update(country);
		CountryDto returnDto = countryMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@GetMapping()
	public ResponseEntity<List<CountryDto>> getAllCountries(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "firstName") String sortCol,
			@RequestParam(defaultValue = "true") Boolean isAsc) {

		log.debug("REST request to get a page of Country, page: {}, size: {}, sortCol: {}, isAsc: {}", page, size,
				sortCol, isAsc);
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));

		Page<Country> entities = countryService.findAll(pageable);

		if (entities.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<CountryDto> dtos = countryMapper.map(entities.getContent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(entities.getTotalElements()));
		headers.add("X-Total-Pages", String.valueOf(entities.getTotalPages()));
		headers.add("X-Current-Page", String.valueOf(entities.getNumber() + 1));
		headers.add("X-Page-Size", String.valueOf(entities.getSize()));

		return ResponseEntity.ok().headers(headers).body(dtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CountryDto> getEmployee(@PathVariable Long id) {
		log.debug("REST request to get Country : {}", id);

		Country entity = countryService.findById(id);
		if (entity != null) {
			CountryDto dto = countryMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		log.debug("REST request to delete Country : {}", id);
		if (!countryService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			countryService.delete(id);
			return ResponseEntity.ok().build();
		}

	}

}
