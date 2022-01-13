package com.springflexcounchdb.dao;

import com.springflexcounchdb.dto.SearchDTO;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeDAO {

	Mono<String> create(Employee e);

	Mono<Employee> update(Employee e);

	Mono<Employee> patch(Employee e);

	Mono<Void> delete(String id);

	Mono<String> createDataBase(String database);

	Flux<Employee> findAll();

	Mono<Employee> findById(String id);

	Flux<Employee> findByName(String name);

	Flux<Employee> findByProperties(SearchDTO searchDTO);

	Flux<Employee> findByProperties(String name, String addressId);

	Flux<Employee> findByPropertiesWithBody(String searchAsString);

}