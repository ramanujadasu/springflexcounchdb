package com.springflexcounchdb.mapping;

import com.springflexcounchdb.common.CommonUtils;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MappingDtoToEntity {

	public static Employee convertEmployeeEntity(EmployeeDTO e̛mployeeDto) {
		return new Employee(e̛mployeeDto.getId(), e̛mployeeDto.getRev(), e̛mployeeDto.getName(), e̛mployeeDto.getAge(),
				CommonUtils.getLocalTime());
	}

	public static Mono<EmployeeDTO> convertEmpoyeeToMono(Mono<Employee> employee) {
		return employee.flatMap(employe -> {
			final EmployeeDTO employeeDTO = new EmployeeDTO(employe.get_id(), employe.get_rev(), employe.getName(), employe.getAge(),
					employe.getCreateTime());

//	                studentDto.setStudents(
//	                        persons.getPersons()
//	                         .stream().filter(person -> person.isStudent())
//	                         .collect(toList())
//	                );
//	                studentDto.setRepresent(
//	                        persons.getRepresent().isStudent()
//	                );
			return Mono.just(employeeDTO);
		});
	}

	public static Flux<EmployeeDTO> convertEmployeeToFlux(Flux<Employee> employee) {
		return employee.flatMap(employe -> {
			final EmployeeDTO employeeDTO =  new EmployeeDTO(employe.get_id(), employe.get_rev(), employe.getName(), employe.getAge(),
					employe.getCreateTime());
			return Flux.just(employeeDTO);
		});
	}
	
	public static Flux<Employee> convertEmployeeDtoToFlux(Flux<EmployeeDTO> employeeDTO) {
		return employeeDTO.flatMap(e̛mployeeDto -> {
			final Employee employee =  new Employee(e̛mployeeDto.getId(), e̛mployeeDto.getRev(), e̛mployeeDto.getName(), e̛mployeeDto.getAge(),
					CommonUtils.getLocalTime());
			return Flux.just(employee);
		});
	}
	
	public static Mono<Employee> convertEmployeeDtoToMono(Mono<EmployeeDTO> employeeDTO) {
		return employeeDTO.flatMap(e̛mployeeDto -> {
			final Employee employee =  new Employee(e̛mployeeDto.getId(), e̛mployeeDto.getRev(), e̛mployeeDto.getName(), e̛mployeeDto.getAge(),
					CommonUtils.getLocalTime());
			return Mono.just(employee);
		});
	}
}
