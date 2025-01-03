package com.hrapp.global.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "job_title")
	private String jobTitle;

	@NotNull
	@Column(name = "min_salary")
	private Long minSalary;

	@NotNull
	@Column(name = "max_salary")
	private Long maxSalary;

	// Relationships

	@ManyToOne
	@JsonIgnoreProperties(value = { "jobs", "manager", "department" }, allowSetters = true)
	private Employee employee;

	@ManyToMany
	@JsonIgnoreProperties(value = { "jobs" }, allowSetters = true)
	@JoinTable(name = "rel_job__task", joinColumns = @JoinColumn(name = "job_id"), inverseJoinColumns = @JoinColumn(name = "task_id"))
	private Set<Task> tasks = new HashSet<>();

}
