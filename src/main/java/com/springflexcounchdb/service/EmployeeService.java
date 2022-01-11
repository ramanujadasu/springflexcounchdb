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
	@Qualifier("employeeDAO2")
	IEmployeeDAO iEmployeeDAO;

	public Flux<Employee> findAll() {
		return iEmployeeDAO.findAll();
	}

	public Long CREATED_DATE = Instant.EPOCH.getEpochSecond();
	public Long LAST_MODIFIED_DATE = Instant.EPOCH.getEpochSecond();

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
		employee.setCreatedOn(CREATED_DATE);
		employee.setLastModifiedBy(CommonUtils.LAST_MODIFIED_BY);
		employee.setLastModifiedOn(LAST_MODIFIED_DATE);
		employee.setStatus(new Short("1"));
		return iEmployeeDAO.create(employee);// Mono.just(resCreateResponseDTO);
	}

	public Flux<Employee> findByName(String name) {
		return iEmployeeDAO.findByName(name);
	}

	public Mono<EmployeeDTO> findById(String id) {
		return MappingDtoToEntity.convertEmpoyeeToMono(iEmployeeDAO.findById(id));
	}

	public Mono<EmployeeDTO> update(EmployeeDTO e) {
		return MappingDtoToEntity.convertEmpoyeeToMono(iEmployeeDAO.update(MappingDtoToEntity.convertEmployeeEntity(e)));
	}

	public Mono<EmployeeDTO> delete(String id) {
		return MappingDtoToEntity.convertEmpoyeeToMono(iEmployeeDAO.delete(id));
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
		iEmployeeDAO.createDataBase(database);
		return Mono.just("Database created Successfully.");
	}
}