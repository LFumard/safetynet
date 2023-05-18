package com.openclassrooms.safetynet.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import com.openclassrooms.safetynet.model.DataSource;
import com.openclassrooms.safetynet.model.Person;

@Component
public class PersonRepository {
	private final DataSource dataSource;

	public PersonRepository(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public List<Person> getPersonByAddress(List<String> lstAddressFromStation) {
		List<Person> lstPersonFromAddress = new ArrayList<>();
		for(int iAddress = 0; iAddress < lstAddressFromStation.size(); iAddress++) {
			for(int iPeople = 0; iPeople < dataSource.people.size(); iPeople++) {
				if(dataSource.people.get(iPeople).getAddress().equals(lstAddressFromStation.get(iAddress))) {
					lstPersonFromAddress.add(dataSource.getPersons().get(iPeople));
				}
			}
		}
		if(lstPersonFromAddress.size() < 1)
			return null;
		else return lstPersonFromAddress;
	}

	public List<Person> getPersonByLstName(String lstName) {
		List<Person> lstPersonSameLstName = new ArrayList<>();
		for(int iPerson = 0; iPerson < dataSource.people.size(); iPerson++) {
			if(dataSource.people.get(iPerson).getLastName().equals(lstName)) {
				lstPersonSameLstName.add(dataSource.people.get(iPerson));
			}
		}
		if(lstPersonSameLstName.size() > 0)
			return lstPersonSameLstName;
		else return null;
	}
	
	public Person getPersonByFstNameLstName(String fstName, String lstName) {
		Person PersonSameFstNameLstName = new Person();
		for(int iPerson = 0; iPerson < dataSource.people.size(); iPerson++) {
			if(dataSource.people.get(iPerson).getLastName().equals(lstName) &&
			   dataSource.people.get(iPerson).getFirstName().equals(fstName)) {
				PersonSameFstNameLstName.setFirstName(fstName);
				PersonSameFstNameLstName.setLastName(lstName);
				PersonSameFstNameLstName.setAddress(dataSource.people.get(iPerson).getAddress());
				PersonSameFstNameLstName.setCity(dataSource.people.get(iPerson).getCity());
				PersonSameFstNameLstName.setEmail(dataSource.people.get(iPerson).getEmail());
				PersonSameFstNameLstName.setPhone(dataSource.people.get(iPerson).getPhone());
				PersonSameFstNameLstName.setZip(dataSource.people.get(iPerson).getZip());
				return PersonSameFstNameLstName;
			}
		}
		return null;
	}


	public boolean updatePerson(Person person) {
		for(int iPerson = 0; iPerson < dataSource.people.size(); iPerson++) {
			if(dataSource.people.get(iPerson).getLastName().equals(person.getLastName()) &&
			   dataSource.people.get(iPerson).getFirstName().equals(person.getFirstName())) {
				dataSource.people.get(iPerson).setAddress(person.getAddress());
				dataSource.people.get(iPerson).setCity(person.getCity());
				dataSource.people.get(iPerson).setEmail(person.getEmail());
				dataSource.people.get(iPerson).setPhone(person.getPhone());
				dataSource.people.get(iPerson).setZip(person.getZip());
				return true;
			}
		}
		return false;
	}

	public boolean deletePerson(Person personToDelete) {
		boolean retour;
		List<Person> lstpersonToDelete = new ArrayList<>();
		lstpersonToDelete.add(personToDelete);
		retour = dataSource.people.remove(personToDelete);
		return retour;
	}

	public List<String> getDistinctEmailByCityFromPerson(String city) {
		List<String> lstCommunityEmail = new ArrayList<String>();
		for(int iPerson = 0; iPerson < dataSource.people.size(); iPerson++) {
			if(dataSource.people.get(iPerson).getCity().equals(city))
				if(!lstCommunityEmail.contains(dataSource.people.get(iPerson).getEmail()))
					lstCommunityEmail.add(dataSource.people.get(iPerson).getEmail());
		}
		if(lstCommunityEmail.size() > 0)
		 return lstCommunityEmail;
		else return null;
	}

	public boolean addPerson(Person person) {
		return dataSource.people.add(person);
	}
}
