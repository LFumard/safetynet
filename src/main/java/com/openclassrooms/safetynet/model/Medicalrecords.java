package com.openclassrooms.safetynet.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Medicalrecords {
	private String firstName;
	private String lastName;
	private String birthdate;
	private List<String> medications;
	private List<String> allergies;
	
	public Medicalrecords(String firstName, String lastName, String birthDate, List<String> medication, List<String> allergie) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthDate;
		this.medications = new ArrayList<>(); 
		this.medications = medication;
		this.allergies = new ArrayList<>();
		this.allergies = allergie;
	}
	
	public Medicalrecords() {
	}
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
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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
	public boolean equals(Object obj) {
			if(obj == this) 
				return true;
			return(this.toString().equals(obj.toString()));
	}
	@Override
	public String toString() {
		return "{" +
        " lastName='" + lastName + '\'' +
        ", firstName='" + firstName + '\'' +
        ", birthdate='" + birthdate + '\'' +
        ", allergies='" + allergies + '\'' +
        ", medications='" + medications + '\'' +
        '}';
	}
	
}
