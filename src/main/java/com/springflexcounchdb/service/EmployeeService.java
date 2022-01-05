package com.springflexcounchdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springflexcounchdb.dao.IEmployeeDAO;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.mapping.MappingDtoToEntity;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService implements IEmployeeService {
	@Autowired
	IEmployeeDAO iEmployeeDAO;

	public Flux<EmployeeDTO> findAll() {
		return MappingDtoToEntity.convertEmployeeToFlux(iEmployeeDAO.findAll());
	}

	public Mono<EmployeeDTO> create(EmployeeDTO empl) {
		return MappingDtoToEntity
				.convertEmpoyeeToMono(iEmployeeDAO.create(MappingDtoToEntity.convertEmployeeEntity(empl)));
	}

	public Mono<Object> findByName(String name) {
		return iEmployeeDAO.findByName(name);
	}

	public Mono<EmployeeDTO> findById(String id) {
		return MappingDtoToEntity.convertEmpoyeeToMono(iEmployeeDAO.findById(id));
	}

	public Mono<EmployeeDTO> update(EmployeeDTO e) {
		return MappingDtoToEntity
				.convertEmpoyeeToMono(iEmployeeDAO.update(MappingDtoToEntity.convertEmployeeEntity(e)));
	}

	public Mono<Void> delete(String id) {
		return iEmployeeDAO.delete(id);
	}

}