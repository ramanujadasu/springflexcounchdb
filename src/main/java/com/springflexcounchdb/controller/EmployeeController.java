package com.springflexcounchdb.controller;

import java.util.Map;

import com.springflexcounchdb.model.Employee;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.service.IEmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private IEmployeeService employeeService;

	// private String message= "Given api successfully executed.";

	@GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Employee> findAll() {
		return employeeService.findAll();
		// return CommonUtils.convertResponse(employeeService.findAll(), message,
		// HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public Mono<Employee> findById(@PathVariable("id") String id) {
		return employeeService.findById(id);
	}

	@GetMapping(value = "/find-by-name/{name}")
	public Flux<Employee> findByName(@PathVariable("name") String name) {
		return employeeService.findByName(name);
	}

	@PostMapping(value = "/find2")
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
	public Mono<Employee> findByProperties(@RequestParam String name, @RequestParam String addressId) {

		System.out.println("Name: "+name +", addressId: "+ addressId);
		return employeeService.findByProperties(name, addressId);
	}
	@PostMapping(value = "/create")
	//public Mono<CommonResponse> create(@RequestBody EmployeeDTO employeeDTO) {
	public Mono<Employee> create(@RequestBody EmployeeDTO employeeDTO) {
//	public ResponseEntity<CommonResponse> create(@RequestBody EmployeeDTO employeeDTO) {	

		//CommonResponse cr = new CommonResponse(employeeService.create(employeeDTO), "", HttpStatus.OK);
		// return new ResponseEntity<CommonResponse>(HttpStatus.OK).ok(cr);
		//return Mono.cast(cr).checkpoint().cast(null);
		//return MappingDtoToEntity.convertResponse(employeeService.create(employeeDTO), "", HttpStatus.CREATED);
		
//		Object object = null;
//		try {
//			object = employeeService.create(employeeDTO).toFuture().get();
//			System.out.println("Object: "+ object);
//			
//		} catch (InterruptedException | ExecutionException e) {
//			System.out.println("Exception: "+e.getMessage());
//		}
		return employeeService.create(employeeDTO);//Mono.just(new CommonResponse(object, "Given data inserted succesfully", HttpStatus.OK.value()));
	}

	@PutMapping(value = "/update2")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Employee> update2(@RequestBody EmployeeDTO employeeDTO,
			@RequestParam(required = true, name = "id") String id,
			@RequestParam(required = true, name = "rev_id") String revId) {
		employeeDTO.setId(id);
		// employeeDTO.setRev(revId);
		return employeeService.update(employeeDTO);
	}
	
	@PutMapping(value = "/update")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Employee> update(@RequestBody EmployeeDTO employeeDTO,
			@RequestParam(required = true, name = "id") String id) {
		employeeDTO.setId(id);
		return employeeService.update(employeeDTO);
	}

	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.OK)
	public Mono<Employee> delete(@RequestParam(required = true, name = "id") String id,
			@RequestParam(required = true, name = "rev_id") String revId) {
		return employeeService.delete(id, revId);
	}

	@GetMapping(value = "/createDatabase/{database}")
	public Mono<Employee> createDatabase(@PathVariable String database) {
		return employeeService.createDataBase(database);
	}
}
