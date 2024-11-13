package com.global.dto;

import com.global.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

	private Long id;

	private String login;

	public UserDTO(User user) {
		this.id = user.getId();
		// Customize it here if you need
		this.login = user.getLogin();
	}
}
