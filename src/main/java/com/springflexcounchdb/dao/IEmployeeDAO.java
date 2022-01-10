package com.springflexcounchdb.dao;

import com.springflexcounchdb.dto.SearchDTO;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeDAO {
	
	Flux<Employee> findAll();

	Mono<Employee> findById(String id);

	Flux<Employee> findByName(String name);

	Mono<Employee> create(Employee e);
	
	Mono<Employee> createDataBase(String database);

	Mono<Employee> updateDetails(Employee e);
	
	Mono<Employee> update(Employee e);

	Mono<Employee> delete(String id, String revId);

	Mono<Employee> findByProperties(SearchDTO searchDTO);
	
	Mono<Employee> findByProperties(String name, String addressId);
	
	Mono<Employee> findByProperties(String searchAsString);
	
}