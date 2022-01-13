package com.springflexcounchdb.service;

import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.dto.SearchDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeService {

	Mono<String> create(EmployeeDTO e);

	Mono<EmployeeDTO> update(EmployeeDTO e);

	Mono<EmployeeDTO> patch(EmployeeDTO e);

	Mono<Void> delete(String id);

	Mono<String> createDataBase(String database);

	Flux<EmployeeDTO> findAll();

	Mono<EmployeeDTO> findById(String id);

	Flux<EmployeeDTO> findByName(String name);

	Flux<EmployeeDTO> findByProperties(SearchDTO searchDTO);

	Flux<EmployeeDTO> findByPropertiesWithBody(String searchAsString);

	Flux<EmployeeDTO> findByProperties(String name, String addressId);
}