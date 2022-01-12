package com.springflexcounchdb.model;

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

@Getter
@Setter
public class Employee extends ContractBase {
	@JsonProperty("_id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String couchDbID;
	private String id;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String _rev;
	private String name;
	private Integer age;
	private List<Address> address;

	public Employee(ContractBaseBuilder<?, ?> b) {
		super(b);
		// TODO Auto-generated constructor stub
	}

	public Employee(String createdBy, String createdOn, String lastModifiedBy, String lastModifiedOn, Short status) {
		super(createdBy, createdOn, lastModifiedBy, lastModifiedOn, status);
	}

	public Employee(ContractBaseBuilder<?, ?> b, String couchDbID, String id, String _rev, String name, Integer age,
			List<Address> address) {
		super(b);
		this.couchDbID = couchDbID;
		this.id = id;
		this._rev = _rev;
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public Employee(String couchDbID, String id, String _rev, String name, Integer age, List<Address> address) {
		this.couchDbID = couchDbID;
		this.id = id;
		this._rev = _rev;
		this.name = name;
		this.age = age;
		this.address = address;
	}

}
