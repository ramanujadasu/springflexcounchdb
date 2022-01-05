package com.springflexcounchdb.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springflexcounchdb.dao.IEmployeeDAO;
import com.springflexcounchdb.dto.CreateResponseDTO;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.mapping.MappingDtoToEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService implements IEmployeeService {
	@Autowired
	IEmployeeDAO iEmployeeDAO;

	public Flux<Object> findAll() {
		return iEmployeeDAO.findAll();
	}

	public Mono<CreateResponseDTO> create(EmployeeDTO empl) {
		
//		{
//		    "ok": true,
//		    "id": "1223",
//		    "rev": "1-638ede4e484d9d9c5abcbe60154d33c0"
//		}
		Object obj = iEmployeeDAO.create(MappingDtoToEntity.convertEmployeeEntity(empl));
		CreateResponseDTO resCreateResponseDTO = null;
		ObjectMapper mapper = new ObjectMapper();
		String jsonString;
		try {
			jsonString = mapper.writeValueAsString(obj);
			System.out.println("jsonString: "+ jsonString);
			resCreateResponseDTO = mapper.readValue(jsonString, CreateResponseDTO.class);
			System.out.println("data: "+ resCreateResponseDTO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Error: "+ e.getMessage());
		}
		
		
		return Mono.just(resCreateResponseDTO);
	}

	public Mono<Object> findByName(String name) {
		return iEmployeeDAO.findByName(name);
	}

	public Mono<EmployeeDTO> findById(String id) {
		return MappingDtoToEntity.convertEmpoyeeToMono(iEmployeeDAO.findById(id));
	}

	public Mono<Object> update(EmployeeDTO e) {
		return iEmployeeDAO.update(MappingDtoToEntity.convertEmployeeEntity(e));
	}

	public Mono<Object> delete(String id, String revId) {
		
		return iEmployeeDAO.delete(id, revId);
	}

}