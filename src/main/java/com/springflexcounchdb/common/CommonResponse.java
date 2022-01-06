package com.springflexcounchdb.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonResponse {

	Object object;
	String message;
	Integer statusCode;

}
