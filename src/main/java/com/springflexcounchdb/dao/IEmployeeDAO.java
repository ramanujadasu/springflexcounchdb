package com.springflexcounchdb.dao;

import com.springflexcounchdb.dto.SearchDTO;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeDAO {
	
	Flux<Object> findAll();

	Mono<Employee> findById(String id);

	Mono<Object> findByName(String name);

	Mono<Object> create(Employee e);

	Mono<Object> update(Employee e);

	Mono<Object> delete(String id, String revId);

	Mono<Object> findByProperties(SearchDTO searchDTO);
	
	Mono<Object> findByProperties(String searchAsString);
	
}