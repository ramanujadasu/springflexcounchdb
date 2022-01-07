package com.springflexcounchdb.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeTemp extends ContractBase {
	@JsonProperty("_id")
	private String couchDbID;
	private String id;
	@JsonInclude(Include.NON_NULL)
	private String _rev;
	private String name;
	private Integer age;
	private List<Address> address;

}
