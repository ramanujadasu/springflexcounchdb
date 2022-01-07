package com.springflexcounchdb.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CrudRepository<T, ID> {
    //TODO : Not saving though response is successfull
    Mono<T> save(String uri, String body);
    Mono<T> findById(String uri, ID id);
    Flux<T> findAll(String uri);
    Mono<T> update(String uri, ID id, String body);
    Mono<T> delet(String uri, String body);
    Flux<T> findByName(String uri, String name);
}
