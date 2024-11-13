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

import com.global.dto.LocationDto;
import com.global.entity.Location;
import com.global.mapper.LocationMapper;
import com.global.service.LocationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
@Log4j2
public class LocationController {

	private final LocationService locationService;
	private final LocationMapper locationMapper;

	@PostMapping()
	public ResponseEntity<LocationDto> save(@Valid @RequestBody LocationDto dto) {
		log.debug("REST request to save Location : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Location cannot already have an ID");
		}

		if (dto.getStreetAddress() == null || dto.getStreetAddress().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Street Address is required");
		}
		if (dto.getPostalCode() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Postal Code is required");
		}
		if (dto.getCity() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "City is required");
		}
		if (dto.getStateProvince() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "StateProvince is required");
		}
		if (dto.getCountry() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country is required");
		}

		Location Location = locationMapper.unMap(dto);
		Location entity = locationService.save(Location);
		LocationDto returnDto = locationMapper.map(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
	}

	@PutMapping()
	public ResponseEntity<LocationDto> update(@Valid @RequestBody LocationDto dto) {
		log.debug("REST request to partial update Location partially : {}", dto);

		Location currentLocation = locationService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Location Location = locationMapper.unMap(dto, currentLocation);
		Location entity = locationService.update(Location);
		LocationDto returnDto = locationMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@GetMapping()
	public ResponseEntity<List<LocationDto>> getAllLocations(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "country") String sortCol,
			@RequestParam(defaultValue = "true") Boolean isAsc) {

		log.debug("REST request to get a page of Location, page: {}, size: {}, sortCol: {}, isAsc: {}", page, size,
				sortCol, isAsc);
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));

		Page<Location> entities = locationService.findAll(pageable);

		if (entities.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<LocationDto> dtos = locationMapper.map(entities.getContent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(entities.getTotalElements()));
		headers.add("X-Total-Pages", String.valueOf(entities.getTotalPages()));
		headers.add("X-Current-Page", String.valueOf(entities.getNumber() + 1));
		headers.add("X-Page-Size", String.valueOf(entities.getSize()));

		return ResponseEntity.ok().headers(headers).body(dtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LocationDto> getLocation(@PathVariable Long id) {
		log.debug("REST request to get Location : {}", id);

		Location entity = locationService.findById(id);
		if (entity != null) {
			LocationDto dto = locationMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
		log.debug("REST request to delete Location : {}", id);
		if (!locationService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			locationService.delete(id);
			return ResponseEntity.ok().build();
		}

	}

}
