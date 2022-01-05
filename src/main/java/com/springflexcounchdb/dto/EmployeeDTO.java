package com.springflexcounchdb.dto;

public class EmployeeDTO {

	String id;
	String rev;
	String name;
	Integer age;
	String createTime;

	public EmployeeDTO(String id, String rev, String name, Integer age, String createTime) {
		super();
		this.id = id;
		this.rev = rev;
		this.name = name;
		this.age = age;
		this.createTime = createTime;
	}
	public EmployeeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRev() {
		return rev;
	}

	public void setRev(String rev) {
		this.rev = rev;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
