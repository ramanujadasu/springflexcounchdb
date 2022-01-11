package com.springflexcounchdb.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.springflexcounchdb.dao.IEmployeeDAO;
import com.springflexcounchdb.dto.AddressDTO;
import com.springflexcounchdb.dto.EmployeeDTO;
import com.springflexcounchdb.model.Address;
import com.springflexcounchdb.model.Employee;
import com.springflexcounchdb.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeService.class)
public class EmployeeServiceTest {

	@Autowired
	private EmployeeService employeeService;
	
	@MockBean
	@Qualifier("employeeDAO")
	private IEmployeeDAO employeeDAO;
	
	@Test
	public void createEmployeeTest() {

		List<AddressDTO> addressDTOList= new ArrayList<>(); 
		addressDTOList.add(new com.springflexcounchdb.dto.AddressDTO("address"));
		List<Address> addressList= new ArrayList<>(); 
		addressList.add(new com.springflexcounchdb.model.Address("address"));
		com.springflexcounchdb.dto.EmployeeDTO employeeDTO= new com.springflexcounchdb.dto.EmployeeDTO("1", "1", "1-1234", "Test-Employee", 20, addressDTOList);
		
		com.springflexcounchdb.model.Employee employee = new com.springflexcounchdb.model.Employee("1", "1", "1-1234", "Test-Employee", 20, addressList);
		
		when(employeeDAO.create(employee).thenReturn(Mono.just("1")));
		Mono<String> responseEmployee  = employeeService.create(employeeDTO);
		
		try {
			assertEquals(true, responseEmployee.toFuture().get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}					
	}
	
	@Test
	public void findEmployeeById() {

		List<Address> addressDTOList= new ArrayList<>(); 
		addressDTOList.add(new Address("address"));
		
		Mono<Employee> employee= Mono.just(new Employee("1", "1", "1-1234", "Test-Employee1", 21, addressDTOList));
		
		when(employeeDAO.findById("1")).thenReturn(employee);
		Mono<EmployeeDTO> employeeDTO = employeeService.findById("1");
		
		try {
			assertEquals(employee.toFuture().get().getName(), employeeDTO.toFuture().get().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void findAllEmployees() {

		List<Address> addressList= new ArrayList<>(); 
		addressList.add(new Address("address"));
		Employee employee= new Employee("12", "1", "1-1234", "Test-Employee1", 20, addressList);
		Employee employee2= new Employee("34", "2", "2-1234", "Test-Employee2", 21, addressList);
		Flux<Employee> fluxEmployee= Flux.just(employee, employee2);
		
		when(employeeService.findAll()).thenReturn(fluxEmployee);
		
	}
	
	@Test
	public void updateEmployeeTest() {

		List<AddressDTO> addressDTOList= new ArrayList<>(); 
		addressDTOList.add(new AddressDTO("address"));
		List<Address> addressList= new ArrayList<>(); 
		addressList.add(new Address("address"));
		EmployeeDTO employeeDTO= new EmployeeDTO("1", "1", "1-1234", "Test-Employee_Update", 20, addressDTOList);
		
		Employee employee= new Employee("1", "1", "1-1234", "Test-Employee_Update", 20, addressList);
		
		//Mono<Employee> monoEmployee= Mono.just(new Employee("1", "1", "1-1234", "Test-Employee_Update", 20, addressList));
		Mono<Employee> monoEmployee = Mono.just(employee);
		
		when(employeeDAO.update(employee).thenReturn(monoEmployee));
		Mono<EmployeeDTO> responseEmployee  = employeeService.update(employeeDTO);
		
		try {
			assertEquals(monoEmployee.toFuture().get().getName(), responseEmployee.toFuture().get().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}	
					
	}
	
	@Test
	public void deleteEmployeeTest() {
		
		List<AddressDTO> addressDTOList= new ArrayList<>(); 
		addressDTOList.add(new AddressDTO("address"));
		List<Address> addressList= new ArrayList<>(); 
		addressList.add(new Address("address"));
		
		//EmployeeDTO employeeDTO =  new EmployeeDTO("1", "1", "1-1234", "Test-Employee", 21, addressDTOList);
		//Employee employee = new Employee("1", "1", "1-1234", "Test-Employee", 21, addressList);
		Mono<Void> monoEmployee = null ;
		
		when(employeeDAO.delete("12")).thenReturn(monoEmployee);
		Mono<Void> employeeDTO = employeeService.delete("12");
		
		assertEquals(monoEmployee, employeeDTO);
		
	}
}
