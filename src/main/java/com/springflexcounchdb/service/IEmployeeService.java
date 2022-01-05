package com.springflexcounchdb.service;

import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IEmployeeService {
	
	Flux<EmployeeDTO> findAll();

	Mono<EmployeeDTO> findById(String id);

	Mono<Object> findByName(String name);

	Mono<EmployeeDTO> create(EmployeeDTO e);

	Mono<EmployeeDTO> update(EmployeeDTO e);

	Mono<Void> delete(String id);
}