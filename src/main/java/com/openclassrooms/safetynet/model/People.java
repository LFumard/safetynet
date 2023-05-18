package com.openclassrooms.safetynet.model;

public class People {
	private String firstName;
	private String LastName;
	private String address;
	private String phone;
	
	public People(String firstName, String lastName, String address, String phone) {
		this.firstName = firstName;
		this.LastName = lastName;
		this.address = address;
		this.phone = phone;
	}
	
	public People() {
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
