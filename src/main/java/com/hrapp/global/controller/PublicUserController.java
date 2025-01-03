package com.hrapp.global.controller;

import com.hrapp.global.dto.UserDto;
import com.hrapp.global.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Log4j2
@RequiredArgsConstructor
public class PublicUserController {

	private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections
			.unmodifiableList(Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey"));

	private final UserService userService;

	/**
	 * {@code GET /users} : get all users with only the public informations - calling this are allowed for anyone.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
	 */
	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllPublicUsers(Pageable pageable) {
		log.debug("REST request to get all public User names");
		if (!onlyContainsAllowedProperties(pageable)) {
			return ResponseEntity.badRequest().build();
		}

		final Page<UserDto> page = userService.getAllPublicUsers(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	private boolean onlyContainsAllowedProperties(Pageable pageable) {
		return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
	}


	/**
	 * Gets a list of all roles.
	 * @return a string list of all roles.
	 */
	@GetMapping("/authorities")
	public List<String> getAuthorities() {
		return userService.getAuthorities();
	}


}