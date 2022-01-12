package com.springflexcounchdb.dao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.springflexcounchdb.common.CommonUtils;
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
		return findAll(database);
	}

	public Mono<String> create(Employee empl) {
		String body = CommonUtils.convertEntityToJsonObject(empl);
		System.out.println("Creating  body:" + body);
		return save(database, body);
	}

	public Flux<Employee> findByName(String name) {
		String body = "{ \"selector\":{ \"name\":{ \"$eq\":\"" + name
				+ "\" } }, \"limit\":2, \"skip\":0, \"execution_stats\":true }";
		return findByName(database, body);
	}

	public Mono<Employee> findByProperties(String name, String addressId) {

		String body = "{\"selector\":{\"address\":[{\"addressId\":\"" + addressId + "\"}], \"name\":{ \"$eq\":\"" + name
				+ "\" } }}";
		System.out.println("body: " + body);

		return webClient.post().uri("/" + database + "/_find").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Employee.class);

	}

	public Mono<Employee> findById(String id) {
		return findById(database, id);
	}

	public Mono<Employee> update(Employee empl) {
		return update(database, empl.getCouchDbID(), CommonUtils.convertEntityToJsonObject(empl));
	}

	public Mono<Employee> patch(Employee empl) {
		String id = empl.getCouchDbID();
		String body = CommonUtils.convertEntityToJsonObject(empl);
		System.out.println("body: " + body);
		return patch(database, id, body);
	}

	public Mono<Void> delete(String id) {
		return delete(database, id);
	}

	@Override
	public Mono<String> createDataBase(String databaseName) {

		return createGivenDatabase(databaseName);
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
}