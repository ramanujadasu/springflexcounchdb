package com.springflexcounchdb.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudRepository<T, ID> {
    Mono<T> save(String uri, String body);
    Mono<T> findById(String uri, ID id);
    Flux<T> findAll(String uri);
    Mono<T> update(String uri, ID id, String body);
    Flux<T> findByName(String uri, String name);
	Mono<T> delete(String docName, String body);
}
