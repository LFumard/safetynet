package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.ChildAdress;
import com.openclassrooms.safetynet.model.FoyerAdress;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonInfo;
import com.openclassrooms.safetynet.repository.PersonRepository;

@Service
public class PersonServiceImpl implements PersonService {

		private static final Logger logger = LogManager.getLogger(PersonServiceImpl.class);
		private final PersonRepository personRepository;
		
		private final MedicalrecordsService medicalrecordsService;
		
		public PersonServiceImpl(PersonRepository personRepository, MedicalrecordsService medicalrecordsService) {
		super();
		this.personRepository = personRepository;
		this.medicalrecordsService = medicalrecordsService;
	}

		public List<PersonInfo> getPersonInfo(String frstName, String lstName) {
			
			List<Person> lstPersonSameLstName;
			
			lstPersonSameLstName = personRepository.getPersonByLstName(lstName);
			if(lstPersonSameLstName == null) {
				logger.info("getPersonInfo - No Person by name : " + lstName);
				return null;
			}
			
			List<PersonInfo> personInfoList = new ArrayList<>();
			for(int iPerson = 0; iPerson < lstPersonSameLstName.size(); iPerson++) {
				PersonInfo personInfo = new PersonInfo();
				personInfo.setLastName(lstName);
				personInfo.setAddress(lstPersonSameLstName.get(iPerson).getAddress());
				personInfo.setEmail(lstPersonSameLstName.get(iPerson).getEmail());
				personInfo.setAge(medicalrecordsService.getAgeFromPerson(lstPersonSameLstName.get(iPerson)));
				personInfo.setMedications(medicalrecordsService.getMedicationFromPerson(lstPersonSameLstName.get(iPerson)));
				personInfo.setAllergies(medicalrecordsService.getAllergieFromPerson(lstPersonSameLstName.get(iPerson)));
				personInfoList.add(personInfo);
			}
			return personInfoList;
		}
			
			
		public Person updatePerson(Person person) {
			
			if(personRepository.updatePerson(person)) {
				logger.info("Update Person : " + person);
				return person;
			}
			logger.info("Default to Update Person : " + person);
			return null;
		}
			
			
		public Person deletePerson(String firstName, String lastName) {
			
			Person personToDelete;
			personToDelete = personRepository.getPersonByFstNameLstName(firstName, lastName);
			if(personToDelete != null) {
				if(personRepository.deletePerson(personToDelete)) {
					logger.info("Delete Person : " + personToDelete);
					return personToDelete;
				}
			}
			logger.info("Default to Delete Person : " + firstName + " " + lastName);
			return null;
		}


		public List<String> getCommunityEmail(String city) {
			List<String> lstCommunityEmail;
			lstCommunityEmail = personRepository.getDistinctEmailByCityFromPerson(city);
			if(lstCommunityEmail == null) 
				logger.info("No Community Email in city : " + city);
			return lstCommunityEmail;
		}

		
		public List<ChildAdress> getChildFromAddress(String address) {
			
			List<Person> lstPersonAdress;
			List<String> lstAddress = new ArrayList<String>();
			
			lstAddress.add(address);
			lstPersonAdress = personRepository.getPersonByAddress(lstAddress);
			if(lstPersonAdress == null) {
				logger.info("No Child at the address : " + address);
				return null;
			}
			
			List<ChildAdress> childAdresslist = new ArrayList<>();
			for(int iPersonAdr = 0; iPersonAdr < lstPersonAdress.size(); iPersonAdr++) {
				if(medicalrecordsService.isAChild(lstPersonAdress.get(iPersonAdr))) {
					ChildAdress childadress = new ChildAdress();
					childadress.setFirstName(lstPersonAdress.get(iPersonAdr).getFirstName());
					childadress.setLastName(lstPersonAdress.get(iPersonAdr).getLastName());
					childadress.setAddress(lstPersonAdress.get(iPersonAdr).getAddress());
					childadress.setAge(medicalrecordsService.getAgeFromPerson(lstPersonAdress.get(iPersonAdr)));
					childadress.setFoyerAdress(getOtherPersonFromFoyer(lstPersonAdress, lstPersonAdress.get(iPersonAdr)));
					childAdresslist.add(childadress);
				}
			}
			return childAdresslist;
		}
		
		public List<FoyerAdress> getOtherPersonFromFoyer(List<Person> lstPersonAdress, Person personToExclude) {
			List<FoyerAdress> foyerSameAdresslist = new ArrayList<>();
			
			for(int iPerson = 0; iPerson < lstPersonAdress.size(); iPerson++) {
				if(lstPersonAdress.get(iPerson).getFirstName().equals(personToExclude.getFirstName()) &&
				   lstPersonAdress.get(iPerson).getLastName().equals(personToExclude.getLastName())) {
					// Pas d'ajout de l'enfant dans la liste
				}
				else {
					FoyerAdress foyerAdress = new FoyerAdress();
					foyerAdress.setFirstName(lstPersonAdress.get(iPerson).getFirstName());
					foyerAdress.setLastName(lstPersonAdress.get(iPerson).getLastName());
					foyerSameAdresslist.add(foyerAdress);
				}
			}
			return foyerSameAdresslist;
		}

		public Person addPerson(Person person) {
			Person personToAdd;
			personToAdd = personRepository.getPersonByFstNameLstName(person.getFirstName(), person.getLastName());
			if(personToAdd == null) {
				if(personRepository.addPerson(person)) {
					logger.info("Add Person : " + person);
					return person;
				}
			}
			logger.info("Default to Add Person : " + person);
			return null;
		}

		
}