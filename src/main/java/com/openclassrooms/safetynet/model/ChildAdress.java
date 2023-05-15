package com.openclassrooms.safetynet.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ChildAdress {
	private String firstName;
	private String lastName;
	private String address;	
	private int age;
	private List<FoyerAdress> foyerAdress = new ArrayList<>();
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<FoyerAdress> getFoyerAdress() {
		return foyerAdress;
	}
	public void setFoyerAdress(List<FoyerAdress> foyerAdress) {
		this.foyerAdress = foyerAdress;
	}
}

