package com.springflexcounchdb.mapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springflexcounchdb.common.CommonUtils;
import com.springflexcounchdb.dto.AddressDTO;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.model.Address;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MappingDtoToEntity {

	public static Employee convertEmployeeEntity(EmployeeDTO e̛mployeeDto) {
		return new Employee(e̛mployeeDto.getCouchDbID(), e̛mployeeDto.getId(), e̛mployeeDto.getRev(), e̛mployeeDto.getName(),
				e̛mployeeDto.getAge(), convertEmpoyeeDTOToEntity(e̛mployeeDto.getAddress()));
	}

	public static EmployeeDTO convertEmployeeDTOEntity(Employee e̛mployee) {
		return new EmployeeDTO(e̛mployee.getCouchDbID(), e̛mployee.getId(), e̛mployee.get_rev(), e̛mployee.getName(), e̛mployee.getAge(),
				convertAddresEntityToDTO(e̛mployee.getAddress()));
	}

	public static List<Address> convertEmpoyeeDTOToEntity(List<AddressDTO> list) {

		List<Address> addressList = new ArrayList<>();
		list.forEach(addressDTO -> {
			addressList.add(new Address(addressDTO.getAddressId()));
		});
		return addressList;
	}

	public static List<AddressDTO> convertAddresEntityToDTO(List<Address> list) {

		List<AddressDTO> addressList = new ArrayList<>();
		list.forEach(address -> {
			addressList.add(new AddressDTO(address.getAddressId()));
		});
		return addressList;
	}

	public static List<AddressDTO> convertEmpoyeeToDTO(List<Address> list) {

		List<AddressDTO> addressDTOList = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			list.forEach(address -> {
				addressDTOList.add(new AddressDTO(address.getAddressId()));
			});
		}
		return addressDTOList;
	}

	public static Mono<EmployeeDTO> convertEmpoyeeToMono(Mono<Employee> employee) {
		return employee.flatMap(employe -> {
//			final EmployeeDTO employeeDTO = new EmployeeDTO(employe.getCouchDbID(), employe.getId(), employe.get_rev(),
//					employe.getName(), employe.getAge(), convertEmpoyeeToDTO(employe.getAddress()));

			final EmployeeDTO employeeDTO = new EmployeeDTO(employe.getCouchDbID(), employe.getId(), employe.get_rev(),
					employe.getName(), employe.getAge(), convertEmpoyeeToDTO(employe.getAddress()), employe.getCreatedBy(), employe.getCreatedOn(), employe.getLastModifiedBy(), CommonUtils.LAST_MODIFIED_DATE, employe.getStatus());

			
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

	public static Mono<EmployeeDTO> convertObjectToMono(Mono<Object> objEmp) {
		// Mono<Employee> employee = objEmp.cast(Employee.class);

		return objEmp.map(obj -> {
			try {
				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = mapper.writeValueAsString(obj);
				System.out.println("jsonStr -------> " + jsonStr);
				Employee employee = new ObjectMapper().readValue(jsonStr, Employee.class);
				System.out.println("employee -------- >" + employee);
				// EmployeeDTO employeeDTO = new ObjectMapper().readValue((JsonParser) obj,
				// EmployeeDTO.class);
				return convertEmployeeDTOEntity(employee);
				// return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		});

//		return employee.flatMap(employe -> {
//			final EmployeeDTO employeeDTO = new EmployeeDTO(employe.getId(), employe.get_rev(),employe.getName(), employe.getAge(),
//					convertEmpoyeeToDTO(employe.getAddress()));
//
////	                studentDto.setStudents(
////	                        persons.getPersons()
////	                         .stream().filter(person -> person.isStudent())
////	                         .collect(toList())
////	                );
////	                studentDto.setRepresent(
////	                        persons.getRepresent().isStudent()
////	                );
//			return Mono.just(employeeDTO);
//		});
	}

	public static Flux<EmployeeDTO> convertEmployeeToFlux(Flux<Employee> employee) {
		return employee.flatMap(employe -> {
			final EmployeeDTO employeeDTO = new EmployeeDTO(employe.getCouchDbID(), employe.getId(), employe.get_rev(),
					employe.getName(), employe.getAge(), convertEmpoyeeToDTO(employe.getAddress()));
			return Flux.just(employeeDTO);
		});
	}

	public static Flux<Employee> convertEmployeeDtoToFlux(Flux<EmployeeDTO> employeeDTO) {
		return employeeDTO.flatMap(e̛mployeeDto -> {
			final Employee employee = new Employee(e̛mployeeDto.getCouchDbID(), e̛mployeeDto.getId(), e̛mployeeDto.getRev(),
					e̛mployeeDto.getName(), e̛mployeeDto.getAge(),
					convertEmpoyeeDTOToEntity(e̛mployeeDto.getAddress()));
			return Flux.just(employee);
		});
	}

	public static Mono<Employee> convertEmployeeDtoToMono(Mono<EmployeeDTO> employeeDTO) {
		return employeeDTO.flatMap(e̛mployeeDto -> {
			final Employee employee = new Employee(e̛mployeeDto.getCouchDbID(), e̛mployeeDto.getId(), e̛mployeeDto.getRev(),
					e̛mployeeDto.getName(), e̛mployeeDto.getAge(),
					convertEmpoyeeDTOToEntity(e̛mployeeDto.getAddress()));
			return Mono.just(employee);
		});
	}

	public static Flux<EmployeeDTO> convertFluxOfListOfEmployeeToFluxOfEmployeeDTO(Flux<Employee> employeeFlux) {
		List<EmployeeDTO> listOfempDTO = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		employeeFlux.collectList();
		Mono<List<EmployeeDTO>> listEmpMono = employeeFlux.collectList()
				.flatMap(empList -> {
					for(int i=0; i<empList.size();i++){
						List<Employee> emp = new ArrayList<>();
						try {
							String convertedIntoString = mapper.writeValueAsString(empList.get(i));
							emp = mapper.readValue(convertedIntoString, new TypeReference<List<Employee>>(){});
						} catch (IOException e) {
							e.printStackTrace();
						}
						emp.forEach(employee -> {
							EmployeeDTO employeeDTO = new EmployeeDTO(employee.getCouchDbID(), employee.getId(),
									employee.get_rev(), employee.getName(), employee.getAge(),
									convertEmpoyeeToDTO(employee.getAddress()));
							listOfempDTO.add(employeeDTO);
						});
					}
					return Mono.just(listOfempDTO);
				});
		return listEmpMono.flatMapMany(Flux::fromIterable);
	}

}
