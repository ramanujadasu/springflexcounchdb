package com.springflexcounchdb.listener;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springflexcounchdb.common.CouchDbOperationConstant;
import com.springflexcounchdb.service.EmployeeService;

import reactor.core.Disposable;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOG = LoggerFactory.getLogger(StartupApplicationListener.class);

	public static int counter = 0;

	@Autowired
	EmployeeService employeeService;

	protected final WebClient webClient;
	private static final String SLASH = "/";
	private Duration duration = Duration.ofMillis(5_000);

	public StartupApplicationListener(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		String docName = "product_family";

		// Need to pass the product family
		String body = convertEntityToJsonObject("{\"_id\":\"testdoc\",\"url\":\"http://localhost:8080\"}");

		createProductFamiltyDatabase(docName, body);

	}

	public void createProductFamiltyDatabase(String docName, String body) {

		Disposable disposable = webClient.put().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(JsonNode.class)
				.map(jsonNode -> String.format(CouchDbOperationConstant.REV_VAL, jsonNode.get("ok")))
				.doAfterSuccessOrError((i, e) -> LOG.info("AfterSuccessOrError: " + i)).doFinally(s -> {

					LOG.info("Finally called");
				}).doOnSuccess(s -> LOG.info("Success")).subscribe(i -> {
					createProductFamiltyData(docName, body);
					LOG.info("Result: " + i);
				});
		;
		LOG.info("disposable2:" + disposable);

	}

	public void createProductFamiltyData(String docName, String body) {
		Disposable disposable = webClient.post().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(JsonNode.class).timeout(duration)
				.map(jsonNode -> String.format(CouchDbOperationConstant.ID_IN_JSON_FORMAT, jsonNode.get("id")))
				.subscribe();
		LOG.info("disposable:" + disposable);
	}

	public static String convertEntityToJsonObject(Object obj) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonObj = "";
		try {
			jsonObj = mapper.writeValueAsString(obj);
			LOG.info("ResultingJSONstring = " + jsonObj);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
}
