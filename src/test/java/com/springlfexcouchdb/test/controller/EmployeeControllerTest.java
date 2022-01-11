package com.springlfexcouchdb.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.springflexcounchdb.controller.EmployeeController;
import com.springflexcounchdb.dto.AddressDTO;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.model.Address;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
public class EmployeeControllerTest {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void createEmployeeTest() {

		List<AddressDTO> addressDTOList= new ArrayList<>(); 
		addressDTOList.add(new AddressDTO("address"));
		EmployeeDTO employeeDTO= new EmployeeDTO("1", "1", "1-1234", "Test-Employee", 20, addressDTOList);
		
		Flux<Employee> responseBody= 
				webTestClient.post().uri("/employees/create")
													.body(Mono.just(employeeDTO), EmployeeDTO.class)
													.exchange()
													.expectStatus()
													.isOk()
													.returnResult(Employee.class)
													.getResponseBody();
		
		StepVerifier.create(responseBody)
					.expectSubscription()
					.expectNextMatches(emp-> emp.getName().equals("Test-Employee"))
					.verifyComplete();
	}
	
	@Test
	public void findEmployeeById() {

		List<AddressDTO> addressDTOList= new ArrayList<>(); 
		addressDTOList.add(new AddressDTO("address"));
		
		Mono<EmployeeDTO> employee= Mono.just(new EmployeeDTO("1", "1", "1-1234", "Test-Employee1", 21, addressDTOList));
		
		Flux<EmployeeDTO> responseBody= 
				webTestClient.get().uri("/employees/1")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(EmployeeDTO.class)
				.getResponseBody();
		
		StepVerifier.create(responseBody)
					.expectSubscription()
					.expectNextMatches(emp-> emp.getName().equals("Test-Employee1"))
					.verifyComplete();
	}
	
	@Test
	public void findAllEmployees() {

		List<Address> addressList= new ArrayList<>(); 
		addressList.add(new Address("address"));
		Employee employee= new Employee("12", "1", "1-1234", "Test-Employee1", 20, addressList);
		Employee employee2= new Employee("34", "2", "2-1234", "Test-Employee2", 21, addressList);
		Flux<Employee> fluxEmployee= Flux.just(employee, employee2);
		
		
		Flux<Employee> responseBody= 
				webTestClient.get().uri("/employees/all")
				.exchange()
				.expectStatus()
				.isOk()
				.returnResult(Employee.class)
				.getResponseBody();
		
		StepVerifier.create(responseBody)
					.expectSubscription()
					.expectNextMatches(emp-> emp.getName().equals("Test-Employee1"))
					.expectNextMatches(emp-> emp.getName().equals("Test-Employee2"))
					.verifyComplete();
	}
	
	
	@Test
	public void updateEmployeeTest() {

		List<AddressDTO> addressDTOList= new ArrayList<>(); 
		addressDTOList.add(new AddressDTO("address"));
		List<Address> addressList= new ArrayList<>(); 
		addressList.add(new Address("address"));
		EmployeeDTO employeeDTO= new EmployeeDTO("1", "1", "1-1234", "Test-Employee", 21, addressDTOList);
		
		Mono<Object> employee= Mono.just(new Employee("1234", "1", "1-1234", "Test-Employee", 20, addressList));
		
		
		Flux<EmployeeDTO> responseBody = 
	         webTestClient.put().uri("/employees/update/1")
						  .body(Mono.just(employeeDTO), EmployeeDTO.class)
						  .exchange()
						  .expectStatus().isOk()
						  .returnResult(EmployeeDTO.class)
						  .getResponseBody();
		
		StepVerifier.create(responseBody)
					.expectSubscription()
					.expectNextMatches(emp-> emp.getName().equals("Test-Employee"))
					.verifyComplete();
	}
	
	@Test
	public void deleteEmployeeTest() {
		
		List<AddressDTO> addressDTOList= new ArrayList<>(); 
		addressDTOList.add(new AddressDTO("address"));
		EmployeeDTO employee= new EmployeeDTO("1", "1", "1-1234", "Test-Employee", 21, addressDTOList);
		
		
		webTestClient.delete().uri("/employees/delete/1")
							  .exchange()
							  .expectStatus()
							  .isOk();
		
	}

}
