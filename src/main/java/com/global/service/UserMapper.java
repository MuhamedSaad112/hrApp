package com.global.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

import com.global.dto.AdminUserDTO;
import com.global.dto.UserDTO;
import com.global.entity.Authority;
import com.global.entity.User;


@Service
public class UserMapper {

	public List<UserDTO> usersToUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
	}

	public UserDTO userToUserDTO(User user) {
		return new UserDTO(user);
	}

	public List<AdminUserDTO> usersToAdminUserDTOs(List<User> users) {
		return users.stream().filter(Objects::nonNull).map(this::userToAdminUserDTO).collect(Collectors.toList());
	}

	public AdminUserDTO userToAdminUserDTO(User user) {
		return new AdminUserDTO(user);
	}

	public List<User> userDTOsToUsers(List<AdminUserDTO> userDTOs) {
		return userDTOs.stream().filter(Objects::nonNull).map(this::userDTOToUser).collect(Collectors.toList());
	}

	public User userDTOToUser(AdminUserDTO userDTO) {
		if (userDTO == null) {
			return null;
		} else {
			User user = new User();
			user.setId(userDTO.getId());
			user.setLogin(userDTO.getLogin());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setEmail(userDTO.getEmail());
			user.setImageUrl(userDTO.getImageUrl());
			user.setActivated(userDTO.isActivated());
			Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
			user.setAuthorities(authorities);
			return user;
		}
	}

	private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
		Set<Authority> authorities = new HashSet<>();

		if (authoritiesAsString != null) {
			authorities = authoritiesAsString.stream().map(string -> {
				Authority auth = new Authority();
				auth.setName(string);
				return auth;
			}).collect(Collectors.toSet());
		}

		return authorities;
	}

	public User userFromId(Long id) {
		if (id == null) {
			return null;
		}
		User user = new User();
		user.setId(id);
		return user;
	}

	@Named("id")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	public UserDTO toDtoId(User user) {
		if (user == null) {
			return null;
		}
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		return userDto;
	}

	@Named("idSet")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	public Set<UserDTO> toDtoIdSet(Set<User> users) {
		if (users == null) {
			return Collections.emptySet();
		}

		Set<UserDTO> userSet = new HashSet<>();
		for (User userEntity : users) {
			userSet.add(this.toDtoId(userEntity));
		}

		return userSet;
	}

	@Named("login")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "login", source = "login")
	public UserDTO toDtoLogin(User user) {
		if (user == null) {
			return null;
		}
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setLogin(user.getLogin());
		return userDto;
	}

	@Named("loginSet")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "login", source = "login")
	public Set<UserDTO> toDtoLoginSet(Set<User> users) {
		if (users == null) {
			return Collections.emptySet();
		}

		Set<UserDTO> userSet = new HashSet<>();
		for (User userEntity : users) {
			userSet.add(this.toDtoLogin(userEntity));
		}

		return userSet;
	}
}