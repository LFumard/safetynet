package com.openclassrooms.safetynet.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.Medicalrecords;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class MedicalrecordsServiceImpl implements MedicalrecordsService {
	
		private static final Logger logger = LogManager.getLogger(MedicalrecordsServiceImpl.class);
	
		private final MedicalRecordRepository medicalrecordRepository;
		
		public MedicalrecordsServiceImpl(MedicalRecordRepository medicalrecordRepository) {
			this.medicalrecordRepository = medicalrecordRepository;
		}
		
		@Override
		public List<Medicalrecords> getMedicalrecords() {
			return medicalrecordRepository.getAllMedicalrecords();
		}

		@Override
		public Medicalrecords addMedicalrecords(Medicalrecords medicalrecord) {
			if(medicalrecordRepository.addMedicalrecords(medicalrecord)) {
				logger.info("Add new MedicalRecord : " + medicalrecord);
				return medicalrecord;
			}
			else {
				logger.info("Default to add new MedicalRecord : " + medicalrecord);
				return null;
			}
		}

		@Override
		public Medicalrecords updateMedicalrecords(Medicalrecords medicalrecord) {
			
			if(medicalrecordRepository.updateMedicalrecords(medicalrecord)) {
				logger.info("Update MedicalRecord : " + medicalrecord);
				return medicalrecord;
			}
			else {
				logger.info("Default to update MedicalRecord : " + medicalrecord);
				return null;
			}
		}
		
		@Override
		public Medicalrecords deleteMedicalrecords(Medicalrecords medicalrecord) {
			
			if(medicalrecordRepository.deleteMedicalrecords(medicalrecord)) {
				logger.info("Delete MedicalRecord : " + medicalrecord);
				return medicalrecord;
			}
			else {
				logger.info("Default to delete MedicalRecord : " + medicalrecord);
				return null;
			}
		}
		
		@Override
	    public List<String> getMedicationFromPerson(Person person) {
			return medicalrecordRepository.getMedicationFromPerson(person);
	    }
		
		@Override
	    public List<String> getAllergieFromPerson(Person person) {
			return medicalrecordRepository.getAllergieFromPerson(person);
	    }
		
		@Override
		public int getAgeFromPerson(Person person) {
				return getAgeFromBirthdate(medicalrecordRepository.getBirthdateFromPerson(person));
			}
		
		@Override
	    public int getAgeFromBirthdate(String birthdate) {
	        LocalDate currentDate = LocalDate.now();
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	            formatter = formatter.withLocale(Locale.FRANCE);
	            LocalDate birthDate = LocalDate.parse(birthdate, formatter);
	            return Period.between(birthDate, currentDate).getYears();
	        } catch (DateTimeParseException e) {
	            logger.info("Birthdate no valid " + e.getMessage());
	        } catch (RuntimeException e) {
	            logger.info("Birthdate no valid " + e.getMessage());
	        }
	        return 0;
	    }

		@Override
		public boolean isAChild(Person person) {
			return(getAgeFromPerson(person) <= 18);
		}
}


