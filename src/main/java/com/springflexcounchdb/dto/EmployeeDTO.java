package com.springflexcounchdb.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {

	String id;
	String rev;
	String name;
	Integer age;
	private List<AddressDTO> address;
	
}
