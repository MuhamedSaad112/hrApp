package com.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * An authority (a security role) used by Spring Security.
 */

@Entity
@Table(name = "sec_authority")
@Setter
@Getter
public class Authority {

	@NotNull
	@Size(max = 50)
	@Id
	@Column(length = 50)
	private String name;

}
