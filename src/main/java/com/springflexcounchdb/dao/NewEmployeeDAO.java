//package com.springflexcounchdb.dao;
//
//import com.springflexcounchdb.model.Employee;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.time.Duration;
//
//@Service
//public class NewEmployeeDAO extends RcpRepository<Employee,String> implements IEmployeeDAO {
//	@Autowired
//	WebClient webClient;
//	private Duration duration = Duration.ofMillis(10_000);
//	private static final String EMPLOYEES_DOC = "employees";
//
//	public NewEmployeeDAO(WebClient webClient) {
//		super(Employee.class, webClient);
//	}
//
//	public Flux<Employee> findAll() {
//		Flux<Employee> res = findAll(EMPLOYEES_DOC);
//		System.out.println(res);
//		return res;
//	}
//
//	public Mono<Employee> create(Employee empl) {
//		String body = "{\"_id\":\""+empl.get_id()+"\",\"name\":\""+empl.getName()+"\",\"age\":"+empl.getAge()+",\"createTime\":\""+empl.getCreateTime()+"\"}";
//		return save(EMPLOYEES_DOC, body);
//	}
//
//	public Mono<Employee> findById(String id) {
//		return findById(EMPLOYEES_DOC, id);
//
//	}
//
//	public Mono<Employee> update(Employee e) {
//		String body = "{\"name\":\""+e.getName()+"\",\"age\":"+e.getAge()+", \"_rev\" :\""+e.get_rev()+"\"}";//"{\""+id+"\": [\""+revId+"\"]}";
////		//{ "123": [ "1-638ede4e484d9d9c5abcbe60154d33c0" ] }
//		return update(EMPLOYEES_DOC,e.get_id(), body);
////		return webClient.put().uri(EMPLOYEES_DOC + e.get_id()).body(Mono.just(e), Employee.class).retrieve()
////				.bodyToMono(Employee.class);
//		//return  null;
//	}
//
//	public Mono<Employee> delete(String id, String revId) {
//
//String body = "{\""+id+"\": [\""+revId+"\"]}";
//////{ "123": [ "1-638ede4e484d9d9c5abcbe60154d33c0" ] }
//		return delet(EMPLOYEES_DOC, body);
//
//		//return webClient.delete().uri(EMPLOYEES_DOC + id).retrieve().bodyToMono(Void.class);
//	}
//
//	public Flux<Employee> findByName(String name) {
////		return webClient.get()
////				.uri("/employees/" + id)
////				.retrieve()
////				/*.onStatus(httpStatus -> HttpStatus.NOT_FOUND.equals(httpStatus),
////                        clientResponse -> Mono.empty())*/
////				.bodyToMono(Employee.class);
//
//		// DAO layer
//		String body = "{\n" +
//				"    \"selector\": {\n" +
//				"        \"name\": {\"$eq\": \"Jenifer\"}\n" +
//				"    }\n" +
//				"}";
//
//		return findByName(EMPLOYEES_DOC,body);
//
//	}
//
//}