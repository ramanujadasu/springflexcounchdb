package com.springflexcounchdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.springflexcounchdb.common.CommonUtils;
import com.springflexcounchdb.dao.IEmployeeDAO;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.dto.SearchDTO;
import com.springflexcounchdb.mapping.MappingDtoToEntity;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService implements IEmployeeService {

	@Autowired
	@Qualifier("employeeDAO")
	IEmployeeDAO iEmployeeDAO;

	public Flux<EmployeeDTO> findAll() {
		return MappingDtoToEntity.convertFluxOfListOfEmployeeToFluxOfEmployeeDTO(iEmployeeDAO.findAll());
	}

	public Mono<String> create(EmployeeDTO empl) {

		Employee employee = MappingDtoToEntity.convertEmployeeEntity(empl);
		employee.setCreatedBy(CommonUtils.CREATED_BY);
		employee.setCreatedOn(CommonUtils.CREATED_DATE);
		employee.setLastModifiedBy(CommonUtils.LAST_MODIFIED_BY);
		employee.setLastModifiedOn(CommonUtils.LAST_MODIFIED_DATE);
		employee.setStatus(new Short("1"));
		return iEmployeeDAO.create(employee);// Mono.just(resCreateResponseDTO);
	}

	public Flux<EmployeeDTO> findByName(String name) {
		return MappingDtoToEntity.convertFluxOfListOfEmployeeToFluxOfEmployeeDTO(iEmployeeDAO.findByName(name));
	}

	public Mono<EmployeeDTO> findById(String id) {

		return MappingDtoToEntity.convertEmpoyeeToMono(iEmployeeDAO.findById(id));
	}

	public Mono<EmployeeDTO> update(EmployeeDTO e) {

		return MappingDtoToEntity
				.convertEmpoyeeToMono(iEmployeeDAO.update(MappingDtoToEntity.convertEmployeeEntity(e)));
	}

	public Mono<EmployeeDTO> patch(EmployeeDTO e) {

		return MappingDtoToEntity.convertEmpoyeeToMono(iEmployeeDAO.patch(MappingDtoToEntity.convertEmployeeEntity(e)));
	}

	public Mono<Void> delete(String id) {
		System.out.println("service delete");
		Mono<Void> deleteResult = iEmployeeDAO.delete(id);
		System.out.println("Deleted document successfully.");
		return deleteResult;
	}

	public Flux<EmployeeDTO> findByProperties(SearchDTO searchDTO) {
		return MappingDtoToEntity
				.convertFluxOfListOfEmployeeToFluxOfEmployeeDTO(iEmployeeDAO.findByProperties(searchDTO));
	}

	public Flux<EmployeeDTO> findByProperties(String name, String addressId) {
		return MappingDtoToEntity
				.convertFluxOfListOfEmployeeToFluxOfEmployeeDTO(iEmployeeDAO.findByProperties(name, addressId));
	}

	public Flux<EmployeeDTO> findByPropertiesWithBody(String searchAsString) {
		return MappingDtoToEntity
				.convertFluxOfListOfEmployeeToFluxOfEmployeeDTO(iEmployeeDAO.findByPropertiesWithBody(searchAsString));
	}

	@Override
	public Mono<String> createDataBase(String database) {

		return iEmployeeDAO.createDataBase(database);
	}
}