package com.global.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.global.dto.EmployeeDto;
import com.global.entity.Employee;

@Mapper
public interface EmployeeMapper {

	@Mapping(source = "id", target = "empId")
	@Mapping(source = "firstName", target = "empFirstName")
	@Mapping(source = "lastName", target = "empLastName")
	@Mapping(source = "email", target = "empEmail")
	@Mapping(source = "phoneNumber", target = "empPhoneNumber")
	@Mapping(source = "hireDate", target = "empHireDate")
	@Mapping(source = "salary", target = "empSalary")
	@Mapping(source = "commissionPct", target = "empCommissionPct")
	@Mapping(target = "fullName", ignore = true)
	EmployeeDto map(Employee entity);

	@AfterMapping
	default void handleFullName(Employee entity, @MappingTarget EmployeeDto dto) {

		if (entity.getFirstName() != null && entity.getLastName() != null) {
			dto.setFullName(entity.getFirstName() + "  " + entity.getLastName());
		}

	}

	List<EmployeeDto> map(List<Employee> entities);

	@Mapping(source = "empId", target = "id")
	@Mapping(source = "empFirstName", target = "firstName")
	@Mapping(source = "empLastName", target = "lastName")
	@Mapping(source = "empEmail", target = "email")
	@Mapping(source = "empPhoneNumber", target = "phoneNumber")
	@Mapping(source = "empHireDate", target = "hireDate")
	@Mapping(source = "empSalary", target = "salary")
	@Mapping(source = "empCommissionPct", target = "commissionPct")
	Employee unMap(EmployeeDto dto);

	@Mapping(source = "empId", target = "id")
	@Mapping(source = "empFirstName", target = "firstName")
	@Mapping(source = "empLastName", target = "lastName")
	@Mapping(source = "empEmail", target = "email")
	@Mapping(source = "empPhoneNumber", target = "phoneNumber")
	@Mapping(source = "empHireDate", target = "hireDate")
	@Mapping(source = "empSalary", target = "salary")
	@Mapping(source = "empCommissionPct", target = "commissionPct")
	Employee unMap(EmployeeDto dto, @MappingTarget Employee entity);

}
