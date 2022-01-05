package com.springflexcounchdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	// private String message= "Given api successfully executed.";

	@GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<EmployeeDTO> findAll() {
		return employeeService.findAll();
		// return CommonUtils.convertResponse(employeeService.findAll(), message,
		// HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public Mono<EmployeeDTO> findById(@PathVariable("id") String id) {
		return employeeService.findById(id);
	}

	@GetMapping(value = "/_find/{name}")
	public Mono<Object> findByName(@PathVariable("name") String name) {
		return employeeService.findByName(name);
	}

	@PostMapping(value = "/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.create(employeeDTO);
	}

	@PutMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<EmployeeDTO> update(@RequestBody EmployeeDTO employeeDTO, @PathVariable("id") String id) {
		employeeDTO.setId(id);
		return employeeService.update(employeeDTO);
	}

	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Void> delete(@PathVariable("id") String id) {
		return employeeService.delete(id);
	}
}
