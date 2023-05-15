package com.openclassrooms.safetynet.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.safetynet.model.ChildAdress;
import com.openclassrooms.safetynet.model.FoyerAdress;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonInfo;
import com.openclassrooms.safetynet.repository.PersonRepository;

@SpringBootTest()
public class PersonServiceTest {

	@MockBean
	private PersonRepository personRepositoryMock;
	
	@MockBean
	private MedicalrecordsService medicalRecordsServiceMock;
	
	@Autowired
	private PersonService personService;
	
	private Person person;
	private List<Person> lstPerson;

	@BeforeEach
    public void setUpEachTest() {

		person = new Person("firstName1", "lastName1", "eMail1", "address1", "city", "zip", "phone");
		lstPerson = new ArrayList<>();
		lstPerson.add(person);
    }
	
	@Test
	public void addPersonWithPersonPrevouslyNotInData() {
		
		Person personTest = new Person("firstName NOT IN DATA", "mylastName", "myaddress", "mycity", "myzip", "myphone", "myemail");
		
		when(personRepositoryMock.getPersonByFstNameLstName(any(String.class), any(String.class))).thenReturn(null);
		when(personRepositoryMock.addPerson(any(Person.class))).thenReturn(true);
		
		assertThat(personService.addPerson(personTest).equals(personTest));
		verify(personRepositoryMock, Mockito.times(1)).addPerson(personTest);
	}
	
	@Test
	public void addPersonWithPersonPrevouslyInData() {

		Person personTest = new Person("firstName1", "lastName1", "myaddress", "mycity", "myzip", "myphone", "myemail");
				
		when(personRepositoryMock.getPersonByFstNameLstName(any(String.class), any(String.class))).thenReturn(personTest);
		when(personRepositoryMock.addPerson(any(Person.class))).thenReturn(false);
				
		assertThat(personService.addPerson(personTest)).isNull();
		verify(personRepositoryMock, Mockito.times(0)).addPerson(personTest);
	}
	
	@Test
	public void updatePersonWithPersonInData() {

		Person personTest = new Person("firstName1", "lastName1", "NEW address", "NEW city", " NEW zip", "NEW phone", "NEW email");
		
		when(personRepositoryMock.updatePerson(any(Person.class))).thenReturn(true);
				
		assertThat(personService.updatePerson(personTest).equals(personTest));
		verify(personRepositoryMock, Mockito.times(1)).updatePerson(personTest);
	}
	
	@Test
	public void updatePersonWithPersonNotInData() {
		
		Person personTest = new Person("firstName1 NOT IN DATA", "lastName1", "NEW address", "NEW city", " NEW zip", "NEW phone", "NEW email");
		
		when(personRepositoryMock.updatePerson(any(Person.class))).thenReturn(false);
				
		assertThat(personService.updatePerson(personTest)).isNull();
	}
	
	@Test
	public void deletePersonWithFirstNameLastNameInData() {
		
		when(personRepositoryMock.getPersonByFstNameLstName(any(String.class), any(String.class))).thenReturn(person);
		when(personRepositoryMock.deletePerson(any(Person.class))).thenReturn(true);
				
		assertThat(personService.deletePerson("firstName1", "lastName1").equals(person));
		verify(personRepositoryMock, Mockito.times(1)).deletePerson(person);
	}
	
	@Test
	public void deletePersonWithFirstNameLastNameNotInData() {
		
		when(personRepositoryMock.getPersonByFstNameLstName(any(String.class), any(String.class))).thenReturn(null);
		when(personRepositoryMock.deletePerson(any(Person.class))).thenReturn(false);
				
		assertThat(personService.deletePerson("firstName1 NOT IN DATA", "lastName1")).isNull();
		verify(personRepositoryMock, Mockito.times(0)).deletePerson(person);

	}
	
	@Test
	public void getPersonInfoWithLastNameNotInData()
    {
		    
		when(personRepositoryMock.getPersonByLstName(anyString())).thenReturn(null);
				
		assertThat(personService.getPersonInfo("firstName1 NOT IN DATA", "lastName1")).isNull();
    }
	
	@Test
	public void getPersonInfoWithLastNameInData()
    {
		
		List<PersonInfo> lstpersonInfoExpected = new ArrayList<>();
		PersonInfo personInfoExpected = new PersonInfo();
		personInfoExpected.setLastName("lastName1");
		personInfoExpected.setAddress("address1");
		personInfoExpected.setEmail("email1");
		personInfoExpected.setAge(20);
		personInfoExpected.setMedications(List.of("medication1"));
		personInfoExpected.setAllergies(List.of("allergie1"));
		lstpersonInfoExpected.add(personInfoExpected);
		
		int ageMock = 20;
		
		when(personRepositoryMock.getPersonByLstName(any(String.class))).thenReturn(lstPerson);
		when(medicalRecordsServiceMock.getAgeFromPerson(any(Person.class))).thenReturn(ageMock);
		when(medicalRecordsServiceMock.getMedicationFromPerson(any(Person.class))).thenReturn(List.of("medication1"));
		when(medicalRecordsServiceMock.getAllergieFromPerson(any(Person.class))).thenReturn(List.of("allergie1"));
		
		assertThat(personService.getPersonInfo("firstName1", "lastName1").equals(lstpersonInfoExpected));
    }
	
	@Test
	public void getCommunityEmailWithCityNotInData() {
		
		when(personRepositoryMock.getDistinctEmailByCityFromPerson(any(String.class))).thenReturn(null);
		
		assertThat(personService.getCommunityEmail("cityNotInData")).isNull();;
	}
	
	@Test
	public void getCommunityEmailWithCityInData() {
		
		when(personRepositoryMock.getDistinctEmailByCityFromPerson(any(String.class))).thenReturn(List.of("eMail1"));
		
		assertThat(personService.getCommunityEmail("city").equals(List.of("eMail1")));
	}
	
	@Test
	public void getChildFromAddressWithAdressInData() {
		
		Person childPerson = new Person("childFirstName", "lastName1", "eMailChild", "address1", "city", "zip", "phone");
		lstPerson.add(childPerson);
		
		List<FoyerAdress> lstfoyerAdressExpected = new ArrayList<>();
		List<ChildAdress> lstChildAdressExpected = new ArrayList<>();
		ChildAdress childAdressExpected = new ChildAdress();
		childAdressExpected.setFirstName("childFirstName");
		childAdressExpected.setLastName("lastName1");
		childAdressExpected.setAddress("address1");
		childAdressExpected.setAge(10);
		FoyerAdress foyerAdressexpected = new FoyerAdress();
		foyerAdressexpected.setFirstName("firstName1");
		foyerAdressexpected.setLastName("lastName1");
		lstfoyerAdressExpected.add(foyerAdressexpected);
		childAdressExpected.setFoyerAdress(lstfoyerAdressExpected);
		lstChildAdressExpected.add(childAdressExpected);
		
		when(personRepositoryMock.getPersonByAddress(List.of("address1"))).thenReturn(lstPerson);
		when(medicalRecordsServiceMock.isAChild(any(Person.class))).thenReturn(true);
		when(medicalRecordsServiceMock.getAgeFromPerson(any(Person.class))).thenReturn(10);
		
		assertThat(personService.getChildFromAddress("address1").equals(lstChildAdressExpected));
	}
	
	@Test
	public void getChildFromAddressWithAdressNotInData() {
		
		when(personRepositoryMock.getPersonByAddress(List.of("address NOT IN DATA"))).thenReturn(null);
		when(medicalRecordsServiceMock.isAChild(any(Person.class))).thenReturn(true);
		when(medicalRecordsServiceMock.getAgeFromPerson(any(Person.class))).thenReturn(10);
		
		assertThat(personService.getChildFromAddress("address NOT IN DATA")).isNull();
	}
	
	@Test
	public void equalsWithCompareTwoPersonInfo() {
		
		PersonInfo personInfo1 = new PersonInfo();
		personInfo1.setLastName("lastName1");
		personInfo1.setAddress("address1");
		personInfo1.setEmail("email1");
		personInfo1.setAge(20);
		personInfo1.setMedications(List.of("medication1"));
		personInfo1.setAllergies(List.of("allergie1"));
		
		PersonInfo personInfo2 = new PersonInfo();
		personInfo2.setLastName("lastName1");
		personInfo2.setAddress("address1");
		personInfo2.setEmail("email1");
		personInfo2.setAge(20);
		personInfo2.setMedications(List.of("medication1"));
		personInfo2.setAllergies(List.of("allergie1"));
		
		assertThat(personInfo1.equals(personInfo2));
	}
}
