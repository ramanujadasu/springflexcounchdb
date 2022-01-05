package com.springflexcounchdb.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ResponseEntity {

	Object result;
	String message;
	Integer statusCode;
}
