package com.openclassrooms.safetynet.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class PersonInfo {
	private String lastName;
	private String address;
	private int age;
	private String email;
	private List<String> medications = new ArrayList<>();
	private List<String> allergies = new ArrayList<>();
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonInfo personInfo = (PersonInfo) o;
        return  Objects.equals(lastName, personInfo.lastName) &&
                Objects.equals(address, personInfo.address) &&
                Objects.equals(age, personInfo.age) &&
                Objects.equals(email, personInfo.email) &&
                Objects.equals(medications, personInfo.medications) &&
                Objects.equals(allergies, personInfo.allergies);
    }
}
