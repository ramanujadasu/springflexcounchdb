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

	public Flux<Employee> findAll() {
		return webClient.get().uri("/employees").retrieve().bodyToFlux(Employee.class)
				.timeout(Duration.ofMillis(10_000));
	}

	public Mono<Employee> create(Employee empl) {
		return webClient.post().uri("/employees").body(Mono.just(empl), Employee.class).retrieve()
				.bodyToMono(Employee.class).timeout(Duration.ofMillis(10_000));
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

	public Mono<Employee> update(Employee e) {
		return webClient.put().uri("/employees/" + e.get_id()).body(Mono.just(e), Employee.class).retrieve()
				.bodyToMono(Employee.class);
	}

	public Mono<Void> delete(String id) {
		return webClient.delete().uri("/employees/" + id).retrieve().bodyToMono(Void.class);
	}

}