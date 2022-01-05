package com.springflexcounchdb.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class EmployeeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	String id;
	String rev;
	String name;
	Integer age;
	LocalDate createTime;
}
