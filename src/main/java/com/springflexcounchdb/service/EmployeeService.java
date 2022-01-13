package com.springflexcounchdb.service;

import java.time.Instant;

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



	//public Mono<Employee> create(EmployeeDTO empl) {
	public Mono<String> create(EmployeeDTO empl) {

//		{
//		    "ok": true,
//		    "id": "1223",
//		    "rev": "1-638ede4e484d9d9c5abcbe60154d33c0"
//		}
//		Object obj = iEmployeeDAO.create(MappingDtoToEntity.convertEmployeeEntity(empl));
//		CreateResponseDTO resCreateResponseDTO = null;
//		ObjectMapper mapper = new ObjectMapper();
//		String jsonString;
//		try {
//			jsonString = mapper.writeValueAsString(obj);
//			System.out.println("jsonString: "+ jsonString);
//			resCreateResponseDTO = mapper.readValue(jsonString, CreateResponseDTO.class);
//			System.out.println("data: "+ resCreateResponseDTO);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.err.println("Error: "+ e.getMessage());
//		}

//		Mono<Object> result = createDataBase("employee1341");
//		System.out.println("Creating database result:"+result);

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
		
		return MappingDtoToEntity.convertEmpoyeeToMono(iEmployeeDAO.update(MappingDtoToEntity.convertEmployeeEntity(e)));
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

	public Mono<Employee> findByProperties(SearchDTO searchDTO) {
		return iEmployeeDAO.findByProperties(searchDTO);
	}

	@Override
	public Mono<Employee> findByProperties(String name, String addressId) {
		return iEmployeeDAO.findByProperties(name, addressId);
	}

	@Override
	public Mono<Employee> findByProperties(String searchAsString) {
		return iEmployeeDAO.findByProperties(searchAsString);
	}

	@Override
	public Mono<String> createDataBase(String database) {
		
		return iEmployeeDAO.createDataBase(database);
	}
}