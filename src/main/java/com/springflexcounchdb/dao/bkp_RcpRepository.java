package com.springflexcounchdb.dao;

import com.springflexcounchdb.repository.bkp_CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class bkp_RcpRepository<T, T1, ID> implements bkp_CrudRepository<T, T1, ID> {

    @Autowired
    WebClient webClient;

    private Duration duration = Duration.ofMillis(10_000);

    @Override
    public Mono<T> save(String uri, String body, Class<T> entity) {
        return webClient.post().uri(uri).accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body)).retrieve()
                .bodyToMono(entity).timeout(duration);
    }

    @Override
    public Mono<T1> findById(String uri, ID id, Class<T1> entity) {
        return webClient.get().uri(uri + "/" + id).retrieve()
                .bodyToMono(entity);
    }

    @Override
    public Flux<T> findAll(String uri, Class<T> entity) {
        return webClient.get().uri(uri).retrieve().bodyToFlux(entity)
                .timeout(duration);
    }

    public Mono<T> update(String uri, ID id, String body, Class<T> entity) {
        return webClient.put().uri(uri+"/"+id).accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body)).retrieve()
                .bodyToMono(entity).timeout(duration);
    }

    @Override
    public Mono<T> delete(String uri, String body, Class<T> entity) {
        return webClient.post().uri(uri).accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body)).retrieve()
                .bodyToMono(entity).timeout(duration);
    }
}
