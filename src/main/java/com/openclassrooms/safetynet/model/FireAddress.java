package com.openclassrooms.safetynet.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class FireAddress {
	private String lastName;
	private String phone;
	private int age;
	private List<String> medications = new ArrayList<>();
	private List<String> allergies = new ArrayList<>();
	
	public FireAddress(String lastName, int age, String phone, List<String> medications, List<String> allergies) {
		this.lastName = lastName;
		this.age = age;
		this.phone = phone;
		this.medications = medications;
		this.allergies = allergies;
	}
	public FireAddress() {
		// TODO Auto-generated constructor stub
	}
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
