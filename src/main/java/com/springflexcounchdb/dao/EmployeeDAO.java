package com.springflexcounchdb.dao;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeDAO implements IEmployeeDAO {
	@Autowired
	WebClient webClient;
	private Duration duration = Duration.ofMillis(10_000);

	public Flux<Object> findAll() {
		return webClient.get().uri("/employees/_all_docs").retrieve().bodyToFlux(Object.class)
				.timeout(duration);
	}

	public Mono<Object> create(Employee empl) {
		
		String body = "{\"_id\":\""+empl.get_id()+"\",\"name\":\""+empl.getName()+"\",\"age\":+"+empl.getAge()+",\"createTime\":\""+empl.getCreateTime()+"\"}";
		
		return webClient.post().uri("/employees").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve()
				.bodyToMono(Object.class).timeout(duration);
	}

	public Mono<Object> findByName(String name) {
//		return webClient.get()
//				.uri("/employees/" + id)
//				.retrieve()
//				/*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
//                        clientResponse -> Mono.empty())*/
//				.bodyToMono(Employee.class);

		// DAO layer
		String body = "{ \"selector\":{ \"name\":{ \"$eq\":\"" + name
				+ "\" } }, \"fields\":[ \"_id\", \"_rev\", \"name\", \"id\" ],\"limit\":2, \"skip\":0, \"execution_stats\":true }";

		Mono<Object> res = webClient.post().uri("/employees/_find").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class);

		return res;

	}

	public Mono<Employee> findById(String id) {
		return webClient.get().uri("/employees/" + id).retrieve()
				/*
				 * .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
				 * clientResponse -> Mono.empty())
				 */
				.bodyToMono(Employee.class);

	}

	public Mono<Object> update(Employee e) {
		
		String body = "{\"name\":\""+e.getName()+"\",\"age\":"+e.getAge()+", \"_rev\" :\""+e.get_rev()+"\"}";//"{\""+id+"\": [\""+revId+"\"]}";
		//{ "123": [ "1-638ede4e484d9d9c5abcbe60154d33c0" ] }
				return webClient.put().uri("/employees/"+e.get_id()).accept(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromObject(body)).retrieve()
						.bodyToMono(Object.class).timeout(duration);
				
				
//		return webClient.put().uri("/employees/" + e.get_id()).body(Mono.just(e), Employee.class).retrieve()
//				.bodyToMono(Employee.class);
	}

	public Mono<Object> delete(String id, String revId) {
		
String body = "{\""+id+"\": [\""+revId+"\"]}";
//{ "123": [ "1-638ede4e484d9d9c5abcbe60154d33c0" ] }
		return webClient.post().uri("/employees/_purge").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve()
				.bodyToMono(Object.class).timeout(duration);
		
		//return webClient.delete().uri("/employees/" + id).retrieve().bodyToMono(Void.class);
	}

}