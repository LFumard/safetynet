package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.model.Medicalrecords;
import com.openclassrooms.safetynet.model.Person;

public interface MedicalrecordsService {
	public List<Medicalrecords> getMedicalrecords();
	public Medicalrecords addMedicalrecords(Medicalrecords medicalrecord);
	public Medicalrecords updateMedicalrecords(Medicalrecords medicalrecords);
	public Medicalrecords deleteMedicalrecords(Medicalrecords medicalrecords);
	public List<String> getMedicationFromPerson(Person person);
	public List<String> getAllergieFromPerson(Person person);
	int getAgeFromBirthdate(String birthdate);
	public int getAgeFromPerson(Person person);
	public boolean isAChild(Person person);
}
