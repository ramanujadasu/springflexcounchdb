package com.springflexcounchdb.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface bkp_CrudRepository<T, T1, ID> {
    //TODO : Not saving though response is successfull
    Mono<T> save(String uri, String body, Class<T> type);
    Mono<T1> findById(String uri, ID id, Class<T1> type);
    Flux<T> findAll(String uri, Class<T> type);
    Mono<T> update(String uri, ID id, String body, Class<T> entity);
    Mono<T> delete(String uri, String body, Class<T> entity);
}
