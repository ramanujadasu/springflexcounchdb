package com.springflexcounchdb.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AllArgsConstructor;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableWebFlux
@AllArgsConstructor
public class WebFluxConfig implements WebFluxConfigurer
{  
	private final Environment env;
	 
	@Bean
	public WebClient getWebClient()
	{
		HttpClient httpClient = HttpClient.create()
		        .tcpConfiguration(client ->
		                client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
		                .doOnConnected(conn -> conn
		                        .addHandlerLast(new ReadTimeoutHandler(10))
		                        .addHandlerLast(new WriteTimeoutHandler(10))));
		
		ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));	    

		return WebClient.builder()
		        .baseUrl(env.getRequiredProperty("couchdb.url"))
		        .defaultHeaders(header -> header.setBasicAuth(env.getRequiredProperty("couchdb.user"), env.getRequiredProperty("couchdb.password")))
		        .clientConnector(connector)
		        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
		        .build();
	}
}