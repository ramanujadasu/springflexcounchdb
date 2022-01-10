package com.springflexcounchdb.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
	
	@JsonProperty("_id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String couchDbID;
	String id;
	@JsonProperty("_rev")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	String rev;
	String name;
	Integer age;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<AddressDTO> address;
//	@Exclude
//	private String message;
	
}
