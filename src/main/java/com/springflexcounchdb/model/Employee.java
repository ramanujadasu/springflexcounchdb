package com.springflexcounchdb.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	String _id;
	String _rev;
	String name;
	Integer age;
	LocalDate createTime;
}
