package com.springflexcounchdb.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
public class Employee extends ContractBase {

	private String id;
	@JsonInclude(Include.NON_NULL)
	private String _rev;
	private String name;
	private Integer age;
	private List<Address> address;

}
