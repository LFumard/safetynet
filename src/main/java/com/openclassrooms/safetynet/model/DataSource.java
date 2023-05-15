package com.openclassrooms.safetynet.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;

@Component
public class DataSource {
	public List <Person> people;
	public List <Firestations> firestations;
	public List <Medicalrecords> medicalrecords;
	
	public DataSource dataSource; 
	
	public List<Person> getPersons() {
		return people;
	}
	public void setPersons(List<Person> persons) {
		this.people = persons;
	}
	public List<Firestations> getFirestations() {
		return firestations;
	}
	public void setFirestations(List<Firestations> firestations) {
		this.firestations = firestations;
	}
	public List<Medicalrecords> getMedicalrecords() {
		return medicalrecords;
	}
	public void setMedicalrecords(List<Medicalrecords> medicalrecords) {
		this.medicalrecords = medicalrecords;
	}

	private static final Logger logger = LogManager.getLogger(DataSource.class);
	
	@PostConstruct
	public void saveJsonData() { 
		logger.info("Begin loading JSON data file");
		try {
			
			byte[] jsonData = Files.readAllBytes(Paths.get("target\\classes\\data.json"));
	
			ObjectMapper objectMapper = new ObjectMapper();
	
			dataSource = objectMapper.readValue(jsonData, DataSource.class);
		
			this.people = dataSource.getPersons();
			this.firestations = dataSource.getFirestations();
			this.medicalrecords = dataSource.getMedicalrecords();
			
		} catch (IOException e) {
            logger.error("Error while parsing input json file : " + e.getMessage());
        }

        logger.info("End loading JSON data file");
	}
}
