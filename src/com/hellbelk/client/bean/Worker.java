package com.hellbelk.client.bean;

import java.io.Serializable;

public class Worker implements Serializable {
	private String firstName;
	private String lastName;
	private long id;
	
	public Worker(){}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return lastName + " " + firstName;
	}
	
	
	
}
