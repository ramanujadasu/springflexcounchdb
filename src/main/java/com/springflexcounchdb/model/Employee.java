package com.springflexcounchdb.model;

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
public class Employee extends ContractBase {
	
	private String id;
	private String _rev;
	private String name;
	private Integer age;
	private List<Address> address;
}
