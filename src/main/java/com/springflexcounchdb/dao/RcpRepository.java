package com.springflexcounchdb.dao;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.springflexcounchdb.common.CouchDbOperationConstant;
import com.springflexcounchdb.repository.CrudRepository;

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
    public Mono<T> save(String docName, String body) {
        return webClient.post().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body)).retrieve()
                .bodyToMono(this.type).timeout(duration);
    }

    @Override
    public Mono<T> findById(String docName, ID id) {
        return webClient.get().uri(SLASH + docName + SLASH + id).retrieve()
                .bodyToMono(this.type).timeout(duration);
    }

    @Override
    public Flux<T> findAll(String docName) {
        return webClient.get().uri(SLASH + docName + CouchDbOperationConstant.FIND_ALL).retrieve().bodyToFlux(this.type)
                .timeout(duration);
    }

    public Mono<T> update(String docName, ID id, String body) {
        return webClient.put().uri(SLASH + docName + SLASH +id).accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body)).retrieve()
                .bodyToMono(this.type).timeout(duration);
    }

    @Override
    /**
     * @param docName databaseName
     * @param body body
     */
    public Mono<T> delete(String docName, String body) {
        return webClient.post().uri(SLASH + docName + CouchDbOperationConstant.DELETE).accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body)).retrieve()
                .bodyToMono(this.type).timeout(duration);
    }

    @Override
    public Flux<T> findByName(String docName, String body) {
        Flux<T> result  = webClient.post().uri(SLASH + docName + CouchDbOperationConstant.FIND).accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body)).retrieve().bodyToFlux(this.type);
        return  result;
    }
}
