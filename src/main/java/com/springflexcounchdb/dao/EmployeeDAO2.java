package com.springflexcounchdb.dao;

import com.springflexcounchdb.common.CommonUtils;
import com.springflexcounchdb.common.CouchDbOperationConstant;
import com.springflexcounchdb.dto.SearchDTO;
import com.springflexcounchdb.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Service("employeeDAO2")
public class EmployeeDAO2 extends RcpRepository<Object, String> implements IEmployeeDAO {
	@Autowired
	WebClient webClient;
	private Duration duration = Duration.ofMillis(10_000);
	
	public String database = "employees";

	public EmployeeDAO2(WebClient webClient) {
		super(Object.class, webClient);
	}

	public Flux<Object> findAll() {
		return findAll(database);
		//return webClient.get().uri("/"+database+"/_all_docs").retrieve().bodyToFlux(Object.class).timeout(duration);
	}

	public Mono<Object> create(Employee empl) {

//		String body = "{\"id\":\"" + empl.get_id() + "\",\"name\":\"" + empl.getName() + "\",\"age\":" + empl.getAge()
//				+ ",\"createTime\":\"" + empl.getCreateTime() + "\"}";
		String body = CommonUtils.convertEntityToJsonObject(empl);
		System.out.println("Creating  body:"+body);
		return save(database, body);
//		return webClient.post().uri("/"+database).accept(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class).timeout(duration);
	}

	public Flux<Object> findByName(String name) {
//		return webClient.get()
//				.uri("/employees/" + id)
//				.retrieve()
//				/*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
//                        clientResponse -> Mono.empty())*/
//				.bodyToMono(Employee.class);

		// DAO layer
		String body = "{ \"selector\":{ \"name\":{ \"$eq\":\"" + name
				+ "\" } }, \"limit\":2, \"skip\":0, \"execution_stats\":true }";
		return findByName(database, body);
//		return webClient.post().uri("/"+database+"/_find").accept(MediaType.APPLICATION_JSON)
//				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class);


	}

	
	public Mono<Object> findByProperties(String name, String addressId) {

		String body = "{\"selector\":{\"address\":[{\"addressId\":\""+addressId+"\"}], \"name\":{ \"$eq\":\""+name+"\" } }}";
		System.out.println("body: "+body);
		Mono<Object> res = webClient.post().uri("/"+database+"/_find").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class);

		return res;

	}
	
	public Mono<Object> findById(String id) {
		return findById(database, id);
//		return webClient.get().uri("/"+database+"/" + id).retrieve()
//				/*
//				 * .onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
//				 * clientResponse -> Mono.empty())
//				 */
//				.bodyToMono(Employee.class);

	}

	public Mono<Object> updateDetails(Employee e) {

		String body = "{\"name\":\"" + e.getName() + "\",\"age\":" + e.getAge() + ", \"_rev\" :\"" + null
				+ "\"}";// "{\""+id+"\": [\""+revId+"\"]}";
		// { "123": [ "1-638ede4e484d9d9c5abcbe60154d33c0" ] }
		return webClient.put().uri("/"+database+"/" + e.getId()).accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class).timeout(duration);

//		return webClient.put().uri("/employees/" + e.get_id()).body(Mono.just(e), Employee.class).retrieve()
//				.bodyToMono(Employee.class);
	}

	
	public Mono<Object> update(Employee empl) {

//		String body = "{\"name\":\"" + e.getName() + "\",\"age\":" + e.getAge() + ", \"_rev\" :\"" + null
//				+ "\"}";// "{\""+id+"\": [\""+revId+"\"]}";
//		// { "123": [ "1-638ede4e484d9d9c5abcbe60154d33c0" ] }
		String body = CommonUtils.convertEntityToJsonObject(empl);
		return webClient.put().uri("/"+database+"/" + empl.getId()).accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class).timeout(duration);

//		return webClient.put().uri("/employees/" + e.get_id()).body(Mono.just(e), Employee.class).retrieve()
//				.bodyToMono(Employee.class);
	}
	
	
	public Mono<Object> delete(String id, String revId) {

		String body = "{\"" + id + "\": [\"" + revId + "\"]}";
//{ "123": [ "1-638ede4e484d9d9c5abcbe60154d33c0" ] }
		return webClient.post().uri("/"+database+"/_purge").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body)).retrieve().bodyToMono(Object.class).timeout(duration);

		// return webClient.delete().uri("/employees/" +
		// id).retrieve().bodyToMono(Void.class);
	}

	@Override
	public Mono<Object> findByProperties(SearchDTO searchDTO) {
		StringBuilder body =new StringBuilder("{");
		
		Map<String, Object> properties =searchDTO.getProperties();
		for(Map.Entry<String, Object> s:properties.entrySet()) {
			if(s.getValue() != null) {
				body.append("\""+s.getKey()+"\":"+s.getValue()+",");
			}
		}
		body.substring(0, body.length()-1);
		body.append("}");
		System.out.println("request body: "+body.toString());
		return webClient.post().uri("/"+database+"/_find").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(body.toString())).retrieve().bodyToMono(Object.class);
	}
	
	@Override
	public Mono<Object> findByProperties(String searchAsString) {

		System.out.println("request searchAsString: "+searchAsString);
		//request searchAsString: {"selector":{"name":{"$eq":"dasu"},"age":{"$gt":10}},"fields":["name","age"],"sort":[{"name":"asc"}],"limit":2,"skip":0,"execution_stats":true}
		return webClient.post().uri("/"+database+"/_find").accept(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromObject(searchAsString)).retrieve().bodyToMono(Object.class);
	}

	@Override
	public Mono<Object> createDataBase(String database) {
		return webClient.put().uri("/"+database).accept(MediaType.APPLICATION_JSON)
				.retrieve().bodyToMono(Object.class);
	}

}