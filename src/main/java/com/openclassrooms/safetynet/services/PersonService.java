package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.model.ChildAdress;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonInfo;

public interface PersonService {
	List<PersonInfo> getPersonInfo(String frstName, String lstName);
	List<ChildAdress> getChildFromAddress(String address);
	Person updatePerson(Person person);
	Person deletePerson(String firstName, String lastName);
	List<String> getCommunityEmail(String city);
	Person addPerson(Person person);
}
