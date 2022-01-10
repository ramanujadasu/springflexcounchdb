package com.springflexcounchdb.dao;

import com.springflexcounchdb.common.CommonUtils;
import com.springflexcounchdb.dto.SearchDTO;
import com.springflexcounchdb.mapping.MappingDtoToEntity;
import com.springflexcounchdb.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service("employeeDAO2")
public class EmployeeDAO2 extends RcpRepository<Employee, String> implements IEmployeeDAO {
	@Autowired
	WebClient webClient;
	private Duration duration = Duration.ofMillis(10_000);

	public String database = "employees";

	public EmployeeDAO2(WebClient webClient) {
		super(Employee.class, webClient);
	}

	public Flux<Employee> findAll() {
		return null;
		// return findAll(database);
		// return
		// webClient.get().uri("/"+database+"/_all_docs").retrieve().bodyToFlux(Object.class).timeout(duration);
	}

	public Mono<Employee> create(Employee empl) {

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

		try {
			System.out.println("Employee getting existing info: " + empl.getId());
			Employee em = (Employee) findById(empl.getId()).toFuture().get();
			empl.set_rev(em.get_rev());
			System.out.println("Employee employeeRevId: " + em.get_rev());
		} catch (InterruptedException | ExecutionException ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		String body = CommonUtils.convertEntityToJsonObject(empl);
		System.out.println("body: "+body);
		return update(database, empl.getId(), body);
	}

	public Mono<Employee> delete(String id) {

		String revId = null;
		try {
			System.out.println("Employee getting existing info: " + id);
			Employee em = (Employee) findById(id).toFuture().get();
			revId = em.get_rev();
			System.out.println("Employee employeeRevId: " + em.get_rev());
		} catch (InterruptedException | ExecutionException ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		String body = "{\"" + id + "\": [\"" + revId + "\"]}";
		System.out.println("body: "+body);
		return delete(database, body);

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
	public Mono<Employee> createDataBase(String database) {

		return webClient.put().uri("/" + database).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(Employee.class);
	}

}