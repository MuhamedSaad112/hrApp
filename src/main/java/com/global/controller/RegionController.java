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

import com.global.dto.RegionDto;
import com.global.entity.Region;
import com.global.mapper.RegionMapper;
import com.global.service.RegionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/v1/region")
@RequiredArgsConstructor
@Log4j2
public class RegionController {

	private final RegionService regionService;
	private final RegionMapper regionMapper;

	@PostMapping()
	public ResponseEntity<RegionDto> save(@Valid @RequestBody RegionDto dto) {
		log.debug("REST request to save Region : {}", dto);

		if (dto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new Region cannot already have an ID");
		}

		if (dto.getRegionName() == null || dto.getRegionName().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Region Name is required");
		}

		Region Region = regionMapper.unMap(dto);
		Region entity = regionService.save(Region);
		RegionDto returnDto = regionMapper.map(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
	}

	@PutMapping()
	public ResponseEntity<RegionDto> update(@Valid @RequestBody RegionDto dto) {
		log.debug("REST request to partial update Region partially : {}", dto);

		Region currentRegion = regionService.getById(dto.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity not found"));

		Region Region = regionMapper.unMap(dto, currentRegion);
		Region entity = regionService.update(Region);
		RegionDto returnDto = regionMapper.map(entity);

		return ResponseEntity.ok(returnDto);
	}

	@GetMapping()
	public ResponseEntity<List<RegionDto>> getAllRegions(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "firstName") String sortCol,
			@RequestParam(defaultValue = "true") Boolean isAsc) {

		log.debug("REST request to get a page of Region, page: {}, size: {}, sortCol: {}, isAsc: {}", page, size,
				sortCol, isAsc);
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortCol));

		Page<Region> entities = regionService.findAll(pageable);

		if (entities.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<RegionDto> dtos = regionMapper.map(entities.getContent());

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Total-Count", String.valueOf(entities.getTotalElements()));
		headers.add("X-Total-Pages", String.valueOf(entities.getTotalPages()));
		headers.add("X-Current-Page", String.valueOf(entities.getNumber() + 1));
		headers.add("X-Page-Size", String.valueOf(entities.getSize()));

		return ResponseEntity.ok().headers(headers).body(dtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RegionDto> getRegion(@PathVariable Long id) {
		log.debug("REST request to get Region : {}", id);

		Region entity = regionService.findById(id);
		if (entity != null) {
			RegionDto dto = regionMapper.map(entity);
			return ResponseEntity.ok(dto);
		}

		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRegion(@PathVariable Long id) {
		log.debug("REST request to delete Region : {}", id);
		if (!regionService.getById(id).isPresent()) {
			return ResponseEntity.noContent().build();
		} else {

			regionService.delete(id);
			return ResponseEntity.ok().build();
		}

	}

}
