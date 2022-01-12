package com.springflexcounchdb.dto;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
	
	
    private String createdBy;
    private String createdOn;
    private String lastModifiedBy;
    private String lastModifiedOn;
    @Min(0)
    @Max(1)
    private Short status;
    
	public EmployeeDTO(String couchDbID, String id, String rev, String name, Integer age, List<AddressDTO> address) {
		super();
		this.couchDbID = couchDbID;
		this.id = id;
		this.rev = rev;
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public EmployeeDTO(String couchDbID, String id, String rev, String name, Integer age, List<AddressDTO> address,
			String createdBy, String createdOn, String lastModifiedBy, String lastModifiedOn,
			@Min(0) @Max(1) Short status) {
		super();
		this.couchDbID = couchDbID;
		this.id = id;
		this.rev = rev;
		this.name = name;
		this.age = age;
		this.address = address;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedOn = lastModifiedOn;
		this.status = status;
	}

	
}
