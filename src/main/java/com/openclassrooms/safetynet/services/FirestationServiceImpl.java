package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.FireAddress;
import com.openclassrooms.safetynet.model.Firestations;
import com.openclassrooms.safetynet.model.People;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonFireAddress;
import com.openclassrooms.safetynet.model.PersonStationNumber;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
//import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;


@Service
public class FirestationServiceImpl implements FirestationsService {

	private static final Logger logger = LogManager.getLogger(FirestationServiceImpl.class);
	
	private final FireStationRepository fireStationRepository;
	private final MedicalRecordRepository medicalRecordRepository;
	private final PersonRepository personRepository;
	private final MedicalrecordsService medicalrecordsService;
	
	public FirestationServiceImpl(FireStationRepository fireStationRepository, MedicalRecordRepository medicalRecordRepository, PersonRepository personRepository, MedicalrecordsService medicalrecordsService) {
		this.fireStationRepository = fireStationRepository;
		this.medicalRecordRepository = medicalRecordRepository;
		this.personRepository = personRepository;
		this.medicalrecordsService = medicalrecordsService;
	}
	
	@Override
	public Firestations addFireStation(Firestations fireStation) { 
		for(int i = 0; i < fireStationRepository.getFirestations().size(); i++) {
			if(fireStationRepository.getFirestations().get(i).getAddress().equals(fireStation.getAddress())) {
				logger.info("Default to add a FireStation (already exist) : " + fireStation.getAddress());
				return null;
			}
		}
		if(fireStationRepository.add(fireStation)) {
			logger.info("Add a FireStation : " + fireStation.getAddress());
			return fireStation;
		}
		else {
			logger.info("Default to add a FireStation : " + fireStation.getAddress());
        	return null;
        }
	}

	@Override
	public Firestations updateFireStation(Firestations fireStation) {
		for(int i = 0; i < fireStationRepository.getFirestations().size(); i++) {
			if(fireStationRepository.getFirestations().get(i).getStation().equals(fireStation.getStation())) {
				if(fireStationRepository.updateFirestations(i,fireStation) != null) {
					logger.info("Update a FireStation : " + fireStation.getAddress());
					return fireStation;
				}
				else {
					logger.info("Default to update a FireStation : " + fireStation.getAddress());
		        	return null;
				}
			}
		}
		logger.info("Default to update a FireStation (FireStation not exist) : " + fireStation.getAddress());
		return null;
	}


	@Override
	public Firestations deleteFireStation(Firestations fireStation) {
		for(int i = 0; i < fireStationRepository.getFirestations().size(); i++) {
			if(fireStationRepository.getFirestations().get(i).getAddress().equals(fireStation.getAddress())) {
				if(fireStationRepository.remove(fireStation)) {
					logger.info("Delete FireStation : " + fireStation.getAddress());
					return fireStation;
				}
				else {
					logger.info("Default to delete a FireStation : " + fireStation.getAddress());
		        	return null;
				}
			}
		}
		logger.info("Default to delete a FireStation (FireStation not exist) : " + fireStation.getAddress());
		return null;
	}

	@Override
	public List<PersonStationNumber> getPersonFromStation(List<String> station_number) {
		List<String> lstAddressFromStation;
		List<Person> lstPersonFromAddress;
		
		lstAddressFromStation = fireStationRepository.getAddressByStationNumber(station_number.get(0));
		if(lstAddressFromStation == null) {
			logger.info("No FireStation at this Station number");
			return null;
		}
		
		lstPersonFromAddress = personRepository.getPersonByAddress(lstAddressFromStation);
		if(lstPersonFromAddress == null) {
			logger.info("No Person for the address at this Station number : " + station_number.get(0));
			return null;
		}
		
		int nbAdult = 0;
		int nbChlid = 0;
		List<People> lstPeople = new ArrayList<>();
		for(int iPerson = 0; iPerson < lstPersonFromAddress.size(); iPerson++) {
			People peopleAdress = new People();
			peopleAdress.setFirstName(lstPersonFromAddress.get(iPerson).getFirstName());
			peopleAdress.setLastName(lstPersonFromAddress.get(iPerson).getLastName());
			peopleAdress.setAddress(lstPersonFromAddress.get(iPerson).getAddress());
			peopleAdress.setPhone(lstPersonFromAddress.get(iPerson).getPhone());
			lstPeople.add(peopleAdress);
			if(medicalrecordsService.isAChild(lstPersonFromAddress.get(iPerson)))
				nbChlid++;
			else nbAdult++;
		}
		
		List<PersonStationNumber> personStation = new ArrayList<>();
		PersonStationNumber personStationNumber = new PersonStationNumber();
		personStationNumber.setPeople(lstPeople);
		personStationNumber.setNumberAdult(nbAdult);
		personStationNumber.setNumberChild(nbChlid);
		personStation.add(personStationNumber);
		return personStation;
	}

	@Override
	public List<String> getPhonePersonFromStation(List<String> station_number) {
		
		List<String> lstAddressFromStation;
		List<Person> lstPersonFromAddress;
	
	
		lstAddressFromStation = fireStationRepository.getAddressByStationNumber(station_number.get(0));
		if(lstAddressFromStation == null) {
			logger.info("No FireStation at this Station number");
			return null;
		}
	
		lstPersonFromAddress = personRepository.getPersonByAddress(lstAddressFromStation);
		if(lstPersonFromAddress == null) {
			logger.info("No Person for the address at this Station number : " + station_number.get(0));
			return null;
		}
	
		List<String> lspPersonPhoneNumber = new ArrayList<>();
		for(int iPerson = 0; iPerson < lstPersonFromAddress.size(); iPerson++) {
			if(!lspPersonPhoneNumber.contains(lstPersonFromAddress.get(iPerson).getPhone()))
				lspPersonPhoneNumber.add(lstPersonFromAddress.get(iPerson).getPhone());
		}
		return lspPersonPhoneNumber;
	}

	@Override
	public List<PersonFireAddress> getpersonFromAddress(List<String> lstAddress) {
		
		List<Person> lstPersonFromAddress;
		
		lstPersonFromAddress = personRepository.getPersonByAddress(lstAddress);
		if(lstPersonFromAddress == null) {
			logger.info("No Person for the address at this Station number");
			return null;
		}
		
		List<PersonFireAddress> personFireAddressList = new ArrayList<>();
		PersonFireAddress personFireAddress = new PersonFireAddress();
		List<FireAddress> fireAddressList = new ArrayList<>();
		
		for(int iPerson = 0; iPerson < lstPersonFromAddress.size(); iPerson++) {
			FireAddress fireAddress = new FireAddress();
			fireAddress.setLastName(lstPersonFromAddress.get(iPerson).getLastName());
			fireAddress.setAge(medicalrecordsService.getAgeFromPerson(lstPersonFromAddress.get(iPerson)));
			fireAddress.setPhone(lstPersonFromAddress.get(iPerson).getPhone());
			fireAddress.setMedications(medicalRecordRepository.getMedicationFromPerson(lstPersonFromAddress.get(iPerson)));
			fireAddress.setAllergies(medicalRecordRepository.getAllergieFromPerson(lstPersonFromAddress.get(iPerson)));
			fireAddressList.add(fireAddress);
		}
	
		personFireAddress.setFireAddress(fireAddressList);
		personFireAddress.setStation(fireStationRepository.getFirestationsFromAddress(lstAddress.get(0)));
		personFireAddressList.add(personFireAddress);
		return personFireAddressList;
	}
}
	
