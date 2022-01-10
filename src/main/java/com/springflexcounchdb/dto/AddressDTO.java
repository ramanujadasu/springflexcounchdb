package com.springflexcounchdb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDTO {
	
	//@JsonProperty("_id")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String addressId;
//	private String flatNumber;
//	private String street;
//	private String city;
//	private String country;
//	private String pincode;


}
