package com.springflexcounchdb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.model.Employee;
import com.springflexcounchdb.service.IEmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private IEmployeeService employeeService;

	@GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Flux<Employee>> findAll() {
		ResponseEntity<Flux<Employee>> response = null;
		try {
			response = new ResponseEntity<Flux<Employee>>(employeeService.findAll(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Flux<Employee>>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		return response;
	}

	@GetMapping(value = "/{id}")
	public Mono<EmployeeDTO> findById(@PathVariable("id") String id) {
		return employeeService.findById(id);
	}

	@GetMapping(value = "/find-by-name/{name}")
	public ResponseEntity<Flux<Employee>> findByName(@PathVariable("name") String name) {
		ResponseEntity<Flux<Employee>> response = null;
		try {
			response = new ResponseEntity<Flux<Employee>>(employeeService.findByName(name), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Flux<Employee>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PostMapping(value = "/create")
	public ResponseEntity<Mono<String>> create(@RequestBody EmployeeDTO employeeDTO) {
		ResponseEntity<Mono<String>> response = null;
		try {
			response = new ResponseEntity<Mono<String>>(employeeService.create(employeeDTO), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Mono<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PutMapping(value = "/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Mono<EmployeeDTO>> update(@RequestBody EmployeeDTO employeeDTO,
			@PathVariable(required = true, name = "id") String id) {
		employeeDTO.setCouchDbID(id);
		ResponseEntity<Mono<EmployeeDTO>> response = null;
		try {
			response = new ResponseEntity<Mono<EmployeeDTO>>(employeeService.update(employeeDTO), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Mono<EmployeeDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@PatchMapping(value = "/patch/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Mono<EmployeeDTO>> patch(@RequestBody EmployeeDTO employeeDTO,
			@PathVariable(required = true, name = "id") String id) {
		employeeDTO.setCouchDbID(id);
		ResponseEntity<Mono<EmployeeDTO>> response = null;
		try {
			response = new ResponseEntity<Mono<EmployeeDTO>>(employeeService.patch(employeeDTO), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Mono<EmployeeDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	

	@DeleteMapping(value = "/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<?>> delete(@PathVariable(required = true, name = "id") String id) {
		Mono<ResponseEntity<?>> response = null;
		try {
			response = Mono.just(new ResponseEntity<>(employeeService.delete(id), HttpStatus.OK));
		} catch (Exception ex) {
			return Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex));
		}
		return response;
	}

	@GetMapping(value = "/createDatabase/{database}")
	public ResponseEntity<Mono<String>> createDatabase(@PathVariable String database) {
		ResponseEntity<Mono<String>> response = null;
		try {
			response = new ResponseEntity<Mono<String>>(employeeService.createDataBase(database), HttpStatus.CREATED);
//		} //catch (DuplicateDataException excep) {
//			return Mono.error(new ResponseStatusException(HttpStatus.CONFLICT,
//					Constant.FIELD_NAME + excep.getRejectedValue(), excep));
		} catch (Exception ex) {
			return new ResponseEntity<Mono<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PostMapping(value = "/find-by-properties")
	public Mono<Employee> findByProperties2(@RequestBody Map<String, Object> searchAsString) {

		// Do whatever with the json as String
		System.out.println(searchAsString);

//	    Gson gson = new Gson();
//        String json = gson.toJson(payload); 
		String json = "";
		try {
			json = new ObjectMapper().writeValueAsString(searchAsString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(json);

		return employeeService.findByProperties(json);
	}

	@GetMapping(value = "/find")
	public Mono<Employee> find(@RequestParam String name, @RequestParam String addressId) {

		System.out.println("Name: " + name + ", addressId: " + addressId);
		return employeeService.findByProperties(name, addressId);
	}
}
