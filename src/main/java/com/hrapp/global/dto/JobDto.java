package com.hrapp.global.dto;

import com.hrapp.global.entity.Employee;
import com.hrapp.global.entity.Task;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {

    private Long id;

    @NotNull
    private String jobTitle;

    @NotNull
    private Long minSalary;

    @NotNull
    private Long maxSalary;

    @NotNull
    private Employee employee;


    private Set<Task> tasks = new HashSet<>();

}
