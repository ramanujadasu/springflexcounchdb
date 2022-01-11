package com.springflexcounchdb.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.springflexcounchdb.common.CommonUtils;
import com.springflexcounchdb.common.CouchDbOperationConstant;
import com.springflexcounchdb.dto.SearchDTO;
import com.springflexcounchdb.model.Employee;
import com.springflexcounchdb.repository.RcpRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service("employeeDAO")
public class EmployeeDAO extends RcpRepository<Employee, String> implements IEmployeeDAO {
	@Autowired
	WebClient webClient;

	public String database = "employees";

	public EmployeeDAO(WebClient webClient) {
		super(Employee.class, webClient);
	}

	public Flux<Employee> findAll() {
		// return null;
		return findAll(database);
		// return
		// webClient.get().uri("/"+database+"/_all_docs").retrieve().bodyToFlux(Object.class).timeout(duration);
	}

	// public Mono<Employee> create(Employee empl) {
	public Mono<String> create(Employee empl) {

//		String body = "{\"id\":\"" + empl.get_id() + "\",\"name\":\"" + empl.getName() + "\",\"age\":" + empl.getAge()
//				+ ",\"createTime\":\"" + empl.getCreateTime() + "\"}";
		String body = CommonUtils.convertEntityToJsonObject(empl);
		System.out.println("Creating  body:" + body);
		return save(database, body);
//		return webClient.post().uri("/"+database).accept(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class).timeout(duration);
	}

	public Flux<Employee> findByName(String name) {
//		return webClient.get()
//				.uri("/employees/" + id)
//				.retrieve()
//				/*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
//                        clientResponse -> Mono.empty())*/
//				.bodyToMono(Employee.class);

		// DAO layer
		String body = "{ \"selector\":{ \"name\":{ \"$eq\":\"" + name
				+ "\" } }, \"limit\":2, \"skip\":0, \"execution_stats\":true }";
		return findByName(database, body);
//		return webClient.post().uri("/"+database+"/_find").accept(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class);

	}

	public Mono<Employee> findByProperties(String name, String addressId) {

		String body = "{\"selector\":{\"address\":[{\"addressId\":\"" + addressId + "\"}], \"name\":{ \"$eq\":\"" + name
				+ "\" } }}";
		System.out.println("body: " + body);
		Mono<Object> res = webClient.post().uri("/" + database + "/_find").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class);

		return null;

	}

	public Mono<Employee> findById(String id) {
		return findById(database, id);
//		return webClient.get().uri("/"+database+"/" + id).retrieve()
//				/*
//				 * .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
//				 * clientResponse -> Mono.empty())
//				 */
//				.bodyToMono(Employee.class);

	}

	public Mono<Employee> update(Employee empl) {
		String id = empl.getId();
//		try {
//			System.out.println("Employee getting existing info: " + empl.getId());
//			Employee em = (Employee) findById(empl.getId()).toFuture().get();
//			empl.set_rev(em.get_rev());
//			System.out.println("Employee employeeRevId: " + em.get_rev());
//		} catch (InterruptedException | ExecutionException ex) {
//			System.out.println("Exception: " + ex.getMessage());
//		}
//		empl.setId(null);
		String body = CommonUtils.convertEntityToJsonObject(empl);
		System.out.println("body: " + body);
		return update(database, id, body);
	}

	public Mono<Void> delete(String id) {

//		String revId = null;
//		try {
//			System.out.println("Employee getting existing info: " + id);
//			Employee em = (Employee) findById(id).toFuture().get();
//			revId = em.get_rev();
//			System.out.println("Employee employeeRevId: " + em.get_rev());
//		} catch (Exception ex) {
//			System.out.println("Exception: " + ex.getMessage());
//		}
//		String body = "{\"" + id + "\": [\"" + revId + "\"]}";
//		System.out.println("body: "+body);

		
		//try 1	
		
//		System.out.println("test11");
//		findById(id).flatMap( (employee) -> { 
//			System.out.println("Employee getting existing info: " + employee.getId());
//			final String body = "{\"" + employee.getId() + "\": [\"" + employee.get_rev() + "\"]}";	
//			Mono<Void> delete = delete(database, body);
//			System.out.println("Employee delete : " + delete);
//			return Mono.just(delete);
//		 });
//		return Mono.just(null);
		
		
		//try2
//		
//		String revId = null;
//		try {
//			System.out.println("Employee getting existing info: " + id);
//			
//			findById(id).zipWhen( (employee) -> {
//			final String body = "{\"" + employee.getId() + "\": [\"" + revId + "\"]}";
//			System.out.println("body: "+body);
//			return delete(database, body)
//		});
//			
//			//Employee em = (Employee) findById(id).toFuture().get();
//			revId = em.get_rev();
//			System.out.println("Employee employeeRevId: " + em.get_rev());
//		} catch (Exception ex) {
//			System.out.println("Exception: " + ex.getMessage());
//		}
		
		
		
	
		return delete(database, id);

	}

	@Override
	public Mono<Employee> findByProperties(SearchDTO searchDTO) {
		StringBuilder body = new StringBuilder("{");

		Map<String, Object> properties = searchDTO.getProperties();
		for (Map.Entry<String, Object> s : properties.entrySet()) {
			if (s.getValue() != null) {
				body.append("\"" + s.getKey() + "\":" + s.getValue() + ",");
			}
		}
		body.substring(0, body.length() - 1);
		body.append("}");
		System.out.println("request body: " + body.toString());
//		return webClient.post().uri("/"+database+"/_find").accept(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromObject(body.toString())).retrieve().bodyToMono(Object.class);
		return null;
	}

	@Override
	public Mono<Employee> findByProperties(String searchAsString) {

		System.out.println("request searchAsString: " + searchAsString);
		// request searchAsString:
		// {"selector":{"name":{"$eq":"dasu"},"age":{"$gt":10}},"fields":["name","age"],"sort":[{"name":"asc"}],"limit":2,"skip":0,"execution_stats":true}
//		return webClient.post().uri("/"+database+"/_find").accept(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromObject(searchAsString)).retrieve().bodyToMono(Object.class);
		return null;
	}

	@Override
	public Mono<String> createDataBase(String database) {

		return webClient.put().uri("/" + database).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(JsonNode.class).map(jsonNode -> String.format(CouchDbOperationConstant.REV_VAL, jsonNode.get("ok")));
	}

}