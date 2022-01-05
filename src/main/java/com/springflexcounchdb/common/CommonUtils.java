package com.springflexcounchdb.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.springflexcounchdb.dto.EmployeeDTO;

import reactor.core.publisher.Flux;

public class CommonUtils {

	public static ResponseEntity convertResponse(Object result, String message, Integer statusCode) {

		return new ResponseEntity(result, message, statusCode);
	}

	public static ResponseEntity convertResponse(Flux<EmployeeDTO> result, String message, Integer statusCode) {
		return new ResponseEntity(result, message, statusCode);
	}

	public static String getLocalTime() {
		LocalDateTime datetime1 = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formatDateTime = datetime1.format(format);
		System.out.println(formatDateTime);
		return formatDateTime;
	}
}
