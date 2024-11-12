package com.global.entity;

import java.time.LocalDate;

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
@Table(name = "job_history")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotEmpty
	@Column(name = "start_date")
	private LocalDate startDate;

	@NotEmpty
	@Column(name = "end_date")
	private LocalDate endDate;

	// Relationships

	@OneToOne
	@JoinColumn(unique = true)
	@JsonIgnoreProperties(value = { "tasks", "employee" }, allowSetters = true)
	private Job job;

	@OneToOne
	@JoinColumn(unique = true)
	@JsonIgnoreProperties(value = { "location", "employees" }, allowSetters = true)
	private Department department;

	@OneToOne
	@JoinColumn(unique = true)
	@JsonIgnoreProperties(value = { "jobs", "manager", "department" }, allowSetters = true)
	private Employee employee;
}
