package com.springflexcounchdb.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springflexcounchdb.dto.EmployeeDTO;

import reactor.core.publisher.Flux;

public class CommonUtils {

	public static final String CREATED_BY = getUsername();

	public static final String LAST_MODIFIED_BY = getUsername();

	public static String getLocalTime() {
		LocalDateTime datetime1 = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formatDateTime = datetime1.format(format);
		System.out.println(formatDateTime);
		return formatDateTime;
	}

	public static String convertEntityToJsonObject(Object obj) {

		ObjectMapper mapper = new ObjectMapper();
		String jsonObj = "";
		try {
			jsonObj = mapper.writeValueAsString(obj);
			System.out.println("ResultingJSONstring = " + jsonObj);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}

	public static String getUsername() {

		return "System";
	}
}
