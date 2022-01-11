package com.springflexcounchdb.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrudRepository<T, ID> {
    Mono<String> save(String uri, String body);

    Mono<T> findById(String uri, ID id);

    Flux<T> findAll(String uri);

    Flux<T> findByName(String uri, String name);


//    Mono<T> save(String uri, String body);
//    Mono<T> findById(String uri, ID id);
//    Flux<T> findAll(String uri);
//    Flux<T> findByName(String uri, String name);

      //TODO
      Mono<T> update(String uri, ID id, String body);
      //TODO
      Mono<T> delete(String docName, String body);
}
