package com.global.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Employee")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotEmpty
	@Column(name = "first_name")
	private String firstName;

	@NotEmpty
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	@Email
	private String email;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "hire_date")
	private LocalDate hireDate;

	@Column(name = "salary")
	@Max(150000)
	@Min(12000)
	@NotNull
	private Long salary;

	@Column(name = "commission_pct")
	private Long commissionPct;

	// Relationships

	@OneToMany(mappedBy = "employee")
	@JsonIgnoreProperties(value = { "tasks", "employee" }, allowSetters = true)
	private Set<Job> jobs = new HashSet<>();

	@ManyToOne
	@JsonIgnoreProperties(value = { "jobs", "manager", "department" }, allowSetters = true)
	private Employee manager;

	@ManyToOne
	@JsonIgnoreProperties(value = { "location", "employees" }, allowSetters = true)
	private Department department;

}
