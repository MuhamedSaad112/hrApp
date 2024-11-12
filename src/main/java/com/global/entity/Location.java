package com.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "location")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotEmpty
	@Column(name = "street_address")
	private String streetAddress;

	@NotEmpty
	@Column(name = "postal_code")
	private String postalCode;

	@NotEmpty
	@Column(name = "city")
	private String city;

	@NotEmpty
	@Column(name = "state_province")
	private String stateProvince;

	// Relationships

	@JsonIgnoreProperties(value = { "region" }, allowSetters = true)
	@OneToOne
	@JoinColumn(unique = true)
	private Country country;

}
