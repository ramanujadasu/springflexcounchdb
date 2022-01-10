package com.springflexcounchdb.service;

import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.dto.SearchDTO;

import com.springflexcounchdb.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeService {

	Flux<Employee> findAll();

	Mono<Employee> findById(String id);

	Flux<Employee> findByName(String name);

	Mono<Employee> create(EmployeeDTO e);

	Mono<Employee> update(EmployeeDTO e);

	Mono<Employee> delete(String id, String revId);

	Mono<Employee> findByProperties(SearchDTO searchDTO);

	Mono<Employee> findByProperties(String searchAsString);
	
	Mono<Employee> findByProperties(String name, String addressId);

	Mono<Employee> createDataBase(String database);

}