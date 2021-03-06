package com.springflexcounchdb.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudRepository<T, ID> {
	
	Mono<String> save(String database, String body);

	Mono<T> findById(String database, ID id);

	Flux<T> findAll(String database);

	Flux<T> find(String database, String name);

	// TODO
	Mono<T> update(String database, ID id, String body);

	Mono<Void> delete(String database, String body);
	
	Mono<String> createGivenDatabase(String database);
}
