//package com.springflexcounchdb.listener;
//
//import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
//
//import java.time.Duration;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.springflexcounchdb.common.CouchDbOperationConstant;
//import com.springflexcounchdb.service.EmployeeService;
//
//import reactor.core.Disposable;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Component
//public class StartupApplicationListenerExample implements ApplicationListener<ContextRefreshedEvent> {
//
//	private static final Logger LOG = LoggerFactory.getLogger(StartupApplicationListenerExample.class);
//
//	public static int counter = 0;
//
//	@Autowired
//	EmployeeService employeeService;
//
//	protected final WebClient webClient;
//	private static final String SLASH = "/";
//	private Duration duration = Duration.ofMillis(5_000);
//
//	public StartupApplicationListenerExample(WebClient webClient) {
//		this.webClient = webClient;
//	}
//
//	String docName = "product_family";
//	String body = "{\"_id\":\"testdoc\",\"url\":\"http://localhost:8080\"}";
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent event) {
//		counter++;
//		LOG.info("Increment counter: " + counter);
//
////		ResponseEntity<Flux<EmployeeDTO>> response = null;
////		try {
////			response = new ResponseEntity<Flux<EmployeeDTO>>(employeeService.findAll(), HttpStatus.OK);
////		} catch (Exception ex) {
////			System.err.println("Exception:" + ex.getMessage());
////		}
////		System.out.println("Output:" + response);
//
////		String docName = "employees";
////		JsonNode response = webClient.get().uri(SLASH + docName + CouchDbOperationConstant.FIND_ALL).retrieve()
////				//.onStatus(HttpStatus.OK, (re -> jsonNode.bodyToFlux(JsonNode.class).timeout(duration).flatMapIterable(jsonNode -> jsonNode.get("rows")));
////				//.onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, response1 -> response1.bodyToMono(String.class).map(Exception::new));
////				.bodyToFlux(JsonNode.class).timeout(duration).flatMapIterable(jsonNode -> jsonNode.get("rows")).blockLast();
////		System.out.println("Output:" + response);
//
//		// String docName = "product_family";
//		// String body = "{\"_id\":\"testdoc\",\"url\":\"http://localhost:8080\"}";
//		createProductFamiltyDatabase(docName);
//		// createProductFamiltyData(docName, body);
//		// createProductFamilyDbAndInsertData(docName, body);
//
//		processStreamOfStrings();
//	}
//
//	public void processStreamOfStrings() {
//		String docName = "employees";
//		// WebClient client = WebClient.create("http://localhost:8080");
//		JsonNode flux = webClient.get().uri(SLASH + docName + CouchDbOperationConstant.FIND_ALL)
//				.accept(TEXT_EVENT_STREAM).retrieve().bodyToFlux(JsonNode.class).timeout(duration)
//				.flatMapIterable(jsonNode -> jsonNode.get("rows")).blockFirst();
//		System.out.println("flux:" + flux);
//		// flux.subscribe(str -> System.out.println("test" + str));
//	}
//
//	public void createProductFamiltyData(String docName, String body) {
//		Disposable flux = webClient.post().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(JsonNode.class).timeout(duration)
//				.map(jsonNode -> String.format(CouchDbOperationConstant.ID_IN_JSON_FORMAT, jsonNode.get("id")))
//				.subscribe();
//		System.out.println("flux2:" + flux);
//		// flux.subscribe(str -> System.out.println("Output:" + str));
//	}
//
//	public void createProductFamiltyDatabase(String docName) {
////		Mono<Object> flux = webClient.put().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON).retrieve()
////				.bodyToMono(JsonNode.class)
////				.map(jsonNode -> String.format(CouchDbOperationConstant.REV_VAL, jsonNode.get("ok")));
////		//System.out.println("flux1:"+flux);
////		flux.subscribe(str -> System.out.println("Output:" + str));
//
//		Disposable flux = webClient.put().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON).retrieve()
//				.bodyToMono(JsonNode.class)
//				.map(jsonNode -> String.format(CouchDbOperationConstant.REV_VAL, jsonNode.get("ok")))
//				.doAfterSuccessOrError((i, e) -> System.out.println("AfterSuccessOrError: " + i)).doFinally(s -> {
//
//					System.out.println("Finally called");
//				}).doOnSuccess(s -> System.out.println("Success")).subscribe(i -> {
//					createProductFamiltyData(docName, body);
//					System.out.println("Result: " + i);
//				});
//		;
//		System.out.println("flux1:" + flux);
//		// flux.subscribe(str -> System.out.println("Output:" + str));
//
//	}
//
//	public void createProductFamilyDbAndInsertData(String docName, String body) {
//
//		Mono<String> response = webClient.put().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON).retrieve()
//				.bodyToMono(JsonNode.class)
//				.map(jsonNode -> String.format(CouchDbOperationConstant.REV_VAL, jsonNode.get("ok"))).zipWhen(
//						firstResponse -> webClient.post().uri(SLASH + docName).accept(MediaType.APPLICATION_JSON)
//								.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(JsonNode.class)
//								.timeout(duration).map(jsonNode -> String
//										.format(CouchDbOperationConstant.ID_IN_JSON_FORMAT, jsonNode.get("id"))),
//						(firstResponse, secondResponse) -> secondResponse);
//
//		response.subscribe(str -> System.out.println("Response:: Output:" + str));
//	}
//
//}
