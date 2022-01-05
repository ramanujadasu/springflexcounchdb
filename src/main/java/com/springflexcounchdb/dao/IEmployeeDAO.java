package com.springflexcounchdb.dao;

import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeDAO {
	
	Flux<Employee> findAll();

	Mono<Employee> findById(String id);

	Mono<Object> findByName(String name);

	Mono<Employee> create(Employee e);

	Mono<Employee> update(Employee e);

	Mono<Void> delete(String id);
}