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

import com.springflexcounchdb.common.CommonUtils;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.service.IEmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author vsrr.ramanujadasu
 *
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;

	/**
	 * 
	 * @param employeeDTO
	 * @return
	 */
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mono<String>> create(@RequestBody EmployeeDTO employeeDTO) {
		ResponseEntity<Mono<String>> response = null;
		try {
			response = new ResponseEntity<Mono<String>>(employeeService.create(employeeDTO), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Mono<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * 
	 * @param employeeDTO
	 * @param id
	 * @return
	 */
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

	/**
	 * 
	 * @param employeeDTO
	 * @param id
	 * @return
	 */
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

	/**
	 * 
	 * @param id
	 * @return
	 */
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

	/**
	 * 
	 * @param database
	 * @return
	 */
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

	/**
	 * 
	 * @return
	 */
	@GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Flux<EmployeeDTO>> findAll() {
		ResponseEntity<Flux<EmployeeDTO>> response = null;
		try {
			response = new ResponseEntity<Flux<EmployeeDTO>>(employeeService.findAll(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Flux<EmployeeDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}")
	public Mono<EmployeeDTO> findById(@PathVariable("id") String id) {
		return employeeService.findById(id);
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping(value = "/find-by-name/{name}")
	public ResponseEntity<Flux<EmployeeDTO>> findByName(@PathVariable("name") String name) {
		ResponseEntity<Flux<EmployeeDTO>> response = null;
		try {
			response = new ResponseEntity<Flux<EmployeeDTO>>(employeeService.findByName(name), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Flux<EmployeeDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * 
	 * @param searchAsString
	 * @return
	 */
	@PostMapping(value = "/find-by-properties")
	public ResponseEntity<Flux<EmployeeDTO>> findByPropertiesWithBody(@RequestBody Map<String, Object> searchAsString) {
		ResponseEntity<Flux<EmployeeDTO>> response = null;
		try {
			System.out.println(searchAsString);
			String givenBody = CommonUtils.convertEntityToJsonObject(searchAsString);
			response = new ResponseEntity<Flux<EmployeeDTO>>(employeeService.findByPropertiesWithBody(givenBody),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Flux<EmployeeDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * 
	 * @param name
	 * @param addressId
	 * @return
	 */
	@GetMapping(value = "/find")
	public ResponseEntity<Flux<EmployeeDTO>> find(@RequestParam String name, @RequestParam String addressId) {

		ResponseEntity<Flux<EmployeeDTO>> response = null;
		try {
			System.out.println("Name: " + name + ", addressId: " + addressId);
			response = new ResponseEntity<Flux<EmployeeDTO>>(employeeService.findByProperties(name, addressId),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Flux<EmployeeDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;

	}
}
