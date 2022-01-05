package com.springflexcounchdb.common;

import com.springflexcounchdb.dto.EmployeeDTO;

import reactor.core.publisher.Flux;

public class CommonUtils {

	public static ResponseEntity convertResponse(Object result, String message, Integer statusCode) {

		return new ResponseEntity(result, message, statusCode);
	}

	public static ResponseEntity convertResponse(Flux<EmployeeDTO> result, String message, Integer statusCode) {
		return new ResponseEntity(result, message, statusCode);
	}

}
