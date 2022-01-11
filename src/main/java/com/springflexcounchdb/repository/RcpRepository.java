package com.springflexcounchdb.repository;

import java.time.Duration;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.springflexcounchdb.common.CouchDbOperationConstant;
import com.springflexcounchdb.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RcpRepository<T, ID> implements CrudRepository<T, ID> {

	protected final Class<T> type;
	protected final WebClient webClient;
	private Duration duration = Duration.ofMillis(5_000);
	private static final String SLASH = "/";

	public RcpRepository(Class<T> type, WebClient webClient) {
		this.type = type;
		this.webClient = webClient;
	}

	@Override
	// public Mono<T> save(String docName, String body) {
	public Mono<String> save(String docName, String body) {

		return webClient.post().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(JsonNode.class).timeout(duration)
				.map(jsonNode -> String.format(CouchDbOperationConstant.ID_IN_JSON_FORMAT, jsonNode.get("id")));

//        return webClient.post().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromObject(body)).retrieve()
//                .bodyToMono(this.type).timeout(duration);
	}

	@Override
	public Mono<T> findById(String docName, ID id) {
		return webClient.get().uri(SLASH + docName + SLASH + id).retrieve().bodyToMono(this.type).timeout(duration);
	}

	@Override
	public Flux<T> findAll(String docName) {
		Flux<JsonNode> response = webClient.get().uri(SLASH + docName + CouchDbOperationConstant.FIND_ALL).retrieve()
				.bodyToFlux(JsonNode.class).timeout(duration).flatMapIterable(jsonNode -> jsonNode.get("rows"));

		Mono<List<T>> monoRes = response.flatMap(res -> findById(docName, (ID) res.get("id").asText())).collectList();
		return (Flux<T>) monoRes.flatMapMany(s -> Flux.just(s));
	}

	public Mono<T> update(String docName, ID id, String body) {
		return webClient.put().uri(SLASH + docName + SLASH + id).accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(this.type).timeout(duration);
	}

	@Override
	/**
	 * @param docName databaseName
	 * @param body    body
	 */
	public Mono<Void> delete(String docName, String id) {
//        return webClient.post().uri(SLASH + docName + CouchDbOperationConstant.DELETE).accept(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromObject(body)).retrieve()
//                .bodyToMono(Void.class).timeout(duration);
		return webClient.get().uri(SLASH + docName + SLASH + id).retrieve().bodyToMono(Employee.class).timeout(duration)
				.zipWhen(firstResponse ->
				webClient.post().uri(SLASH + docName + CouchDbOperationConstant.DELETE)
						.accept(MediaType.APPLICATION_JSON)
						.body(BodyInserters.fromObject("{\"" + id + "\": [\"" + firstResponse.get_rev() + "\"]}")).retrieve()
						.bodyToMono(Void.class).timeout(duration), (firstResponse, secondResponse) -> secondResponse
				);

//		return webClient.post().uri(SLASH + docName + CouchDbOperationConstant.DELETE)
//				.accept(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(body)).retrieve()
//				.bodyToMono(Void.class).timeout(duration);
	}

	@Override
	public Flux<T> findByName(String docName, String body) {
		return (Flux<T>) webClient.post().uri(SLASH + docName + CouchDbOperationConstant.FIND)
				.accept(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(body)).retrieve()
				.bodyToFlux(JsonNode.class).flatMapIterable(jsonNode -> jsonNode.get("docs"));
	}
}
