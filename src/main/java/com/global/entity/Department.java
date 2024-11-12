package com.global.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "department")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Column(name = "department_name", nullable = false)
	private String departmentName;

	// Relationships

	@OneToOne
	@JoinColumn(unique = true)
	@JsonIgnoreProperties(value = { "country" }, allowSetters = true)
	private Location location;

	@OneToMany(mappedBy = "department")
	@JsonIgnoreProperties(value = { "jobs", "manager", "department" }, allowSetters = true)
	private Set<Employee> employees = new HashSet<>();
}
