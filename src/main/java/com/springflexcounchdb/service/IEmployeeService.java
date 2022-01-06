package com.springflexcounchdb.service;

import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.dto.SearchDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeService {

	Flux<Object> findAll();

	Mono<EmployeeDTO> findById(String id);

	Mono<Object> findByName(String name);

	Mono<Object> create(EmployeeDTO e);

	Mono<Object> update(EmployeeDTO e);

	Mono<Object> delete(String id, String revId);

	Mono<Object> findByProperties(SearchDTO searchDTO);

	Mono<Object> findByProperties(String searchAsString);
	
	Mono<Object> findByProperties(String name, String addressId);

	Mono<Object> createDataBase(String database);

}