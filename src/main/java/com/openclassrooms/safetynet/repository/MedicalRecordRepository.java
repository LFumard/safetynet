package com.openclassrooms.safetynet.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.model.DataSource;
import com.openclassrooms.safetynet.model.Medicalrecords;
import com.openclassrooms.safetynet.model.Person;

@Component
public class MedicalRecordRepository {
	private final DataSource dataSource;

	public MedicalRecordRepository(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public List<String> getMedicationFromPerson(Person person) {
		for(int i = 0; i < dataSource.medicalrecords.size(); i++) {
			if(dataSource.medicalrecords.get(i).getFirstName().equals(person.getFirstName()) &&
			   dataSource.medicalrecords.get(i).getLastName().equals(person.getLastName()))
				return (dataSource.medicalrecords.get(i).getMedications());
		}
		return null;
	}

	public List<String> getAllergieFromPerson(Person person) {
		for(int i = 0; i < dataSource.medicalrecords.size(); i++) {
			if(dataSource.medicalrecords.get(i).getFirstName().equals(person.getFirstName()) &&
			   dataSource.medicalrecords.get(i).getLastName().equals(person.getLastName()))
				return (dataSource.medicalrecords.get(i).getAllergies());
		}
		return null;
	}

	public List<Medicalrecords> getAllMedicalrecords() {
		return dataSource.medicalrecords;
	}

	public boolean addMedicalrecords(Medicalrecords medicalrecord) {

		for(int i = 0; i < dataSource.medicalrecords.size(); i++) {
			if (dataSource.medicalrecords.get(i).getFirstName().equals(medicalrecord.getFirstName()) &&
					dataSource.medicalrecords.get(i).getLastName().equals(medicalrecord.getLastName())) {
				return false;
			}
		}
		return dataSource.medicalrecords.add(medicalrecord);
	}

	public boolean updateMedicalrecords(Medicalrecords medicalrecord) {
		for(int i = 0; i < dataSource.medicalrecords.size(); i++) {
			if(dataSource.medicalrecords.get(i).getFirstName().equals(medicalrecord.getFirstName()) &&
			   dataSource.medicalrecords.get(i).getLastName().equals(medicalrecord.getLastName())) {
				dataSource.medicalrecords.set(i, medicalrecord);
				return true;
			}
		}
		return false;
	}

	public boolean deleteMedicalrecords(Medicalrecords medicalrecord) {
		for(int i = 0; i < dataSource.medicalrecords.size(); i++) {
			if(dataSource.medicalrecords.get(i).getFirstName().equals(medicalrecord.getFirstName()) &&
			   dataSource.medicalrecords.get(i).getLastName().equals(medicalrecord.getLastName())) {
				dataSource.medicalrecords.remove(medicalrecord);
				return true;
			}
		}
		return false;
	}

	public String getBirthdateFromPerson(Person person) {
		for(int i = 0; i < dataSource.medicalrecords.size(); i++) {
			if(dataSource.medicalrecords.get(i).getFirstName().equals(person.getFirstName()) &&
			   dataSource.medicalrecords.get(i).getLastName().equals(person.getLastName())) {
				return dataSource.medicalrecords.get(i).getBirthdate();
			}
		}
		return null;
	}
}
