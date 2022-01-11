package com.springflexcounchdb.service;

import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.dto.SearchDTO;

import com.springflexcounchdb.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeService {

	Flux<Employee> findAll();

	Mono<EmployeeDTO> findById(String id);

	Flux<Employee> findByName(String name);

	//Mono<Employee> create(EmployeeDTO e);
	Mono<String> create(EmployeeDTO e);

	Mono<EmployeeDTO> update(EmployeeDTO e);

	Mono<EmployeeDTO> delete(String id);

	Mono<Employee> findByProperties(SearchDTO searchDTO);

	Mono<Employee> findByProperties(String searchAsString);
	
	Mono<Employee> findByProperties(String name, String addressId);

	Mono<String> createDataBase(String database);

}