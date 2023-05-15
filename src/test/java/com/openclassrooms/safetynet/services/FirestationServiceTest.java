package com.openclassrooms.safetynet.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

import com.openclassrooms.safetynet.model.FireAddress;
import com.openclassrooms.safetynet.model.Firestations;
import com.openclassrooms.safetynet.model.People;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonFireAddress;
import com.openclassrooms.safetynet.model.PersonStationNumber;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;


@SpringBootTest()
public class FirestationServiceTest
{
	@MockBean
    private FireStationRepository fireStationRepositoryMock;
	
	@MockBean
	private MedicalRecordRepository medicalRecordRepositoryMock;
	
	@MockBean
	private PersonRepository personRepositoryMock;
	
	@MockBean
	private MedicalrecordsService medicalrecordsServiceMock;
	
	@Autowired
	private FirestationsService fireStationService;

	private Firestations fireStation;
	private List<Firestations> lstfireStation;

	@BeforeEach
    public void setUpEachTest() {

		fireStation = new Firestations();
        fireStation.setStation("1");
        fireStation.setAddress("Address To Station 1");
        lstfireStation = new ArrayList<>();
        lstfireStation.add(fireStation);
    }
	
	@Test
    public void addFireStationWithFireStationInData() {
		
        Firestations fireStationToAdd = new Firestations();
        fireStationToAdd.setStation("1");
        fireStationToAdd.setAddress("Address To Station 1");
        
        when(fireStationRepositoryMock.getFirestations()).thenReturn(lstfireStation);
        when(fireStationRepositoryMock.add(fireStationToAdd)).thenReturn(false);
        
        assertThat(fireStationService.addFireStation(fireStationToAdd)).isEqualTo(null);
        verify(fireStationRepositoryMock, Mockito.times(0)).add(any());
	}
	
	@Test
    public void addFireStationWithFireStationNotInData() {
		
        Firestations fireStationToAdd = new Firestations();
        fireStationToAdd.setStation("2");
        fireStationToAdd.setAddress("Address To Station 2");
        
        when(fireStationRepositoryMock.getFirestations()).thenReturn(lstfireStation);
        when(fireStationRepositoryMock.add(fireStationToAdd)).thenReturn(true);
        
        assertThat(fireStationService.addFireStation(fireStationToAdd)).isEqualTo(fireStationToAdd);
        verify(fireStationRepositoryMock, Mockito.times(1)).add(any());
	}
	
	@Test
    public void updateFireStationWithFireStationInData() {
		
        Firestations fireStationToUpdate = new Firestations();
        fireStationToUpdate.setStation("1");
        fireStationToUpdate.setAddress("New Address To Station 1");
        
        when(fireStationRepositoryMock.getFirestations()).thenReturn(lstfireStation);
        when(fireStationRepositoryMock.updateFirestations(0, fireStationToUpdate)).thenReturn(fireStationToUpdate);
        
        assertThat(fireStationService.updateFireStation(fireStationToUpdate)).isEqualTo(fireStationToUpdate);
        verify(fireStationRepositoryMock, Mockito.times(1)).updateFirestations(0, fireStationToUpdate);
	}
	
	@Test
    public void updateFireStationWithFireStationNotInData() {
		
        Firestations fireStationToUpdate = new Firestations();
        fireStationToUpdate.setStation("NUMBER NOT IN DATA");
        fireStationToUpdate.setAddress("Address To Station 1");
        
        when(fireStationRepositoryMock.getFirestations()).thenReturn(lstfireStation);
        when(fireStationRepositoryMock.updateFirestations(0, fireStationToUpdate)).thenReturn(null);
        
        assertThat(fireStationService.updateFireStation(fireStationToUpdate)).isEqualTo(null);
        verify(fireStationRepositoryMock, Mockito.times(0)).updateFirestations(0, fireStationToUpdate);
	}
	
	@Test
    public void deleteFireStationWithFireStationInData() {
		
        Firestations fireStationToDel = new Firestations();
        fireStationToDel.setStation("1");
        fireStationToDel.setAddress("Address To Station 1");
        
        when(fireStationRepositoryMock.getFirestations()).thenReturn(lstfireStation);
        when(fireStationRepositoryMock.remove(fireStationToDel)).thenReturn(true);
        
        assertThat(fireStationService.deleteFireStation(fireStationToDel)).isEqualTo(fireStationToDel);
        verify(fireStationRepositoryMock, Mockito.times(1)).remove(fireStationToDel);
	}
	
	@Test
    public void deleteFireStationWithFireStationNotInData() {
		
        Firestations fireStationToDel = new Firestations();
        fireStationToDel.setStation("1");
        fireStationToDel.setAddress("Address NOT IN DATA");
        
        when(fireStationRepositoryMock.getFirestations()).thenReturn(lstfireStation);
        when(fireStationRepositoryMock.remove(fireStationToDel)).thenReturn(false);
        
        assertThat(fireStationService.deleteFireStation(fireStationToDel)).isEqualTo(null);
        verify(fireStationRepositoryMock, Mockito.times(0)).remove(fireStationToDel);
	}
	
	@Test
    public void getPersonFromStationWithFireStationInData() {
		
		List<String> lstStationTest = new ArrayList<String>();
		lstStationTest.add("1");
		
		PersonStationNumber PersonStationExpected = new PersonStationNumber();
		List<PersonStationNumber> lstPersonStationExpected = new ArrayList<>();
		List<People> lstPeopleExpected = new ArrayList<>();
		People peopleExpected1 = new People("FirstName1", "LastName1", "Address To Station 1", "Phone1"); 
		People peopleExpected2 = new People("FirstName2", "LastName2", "Address To Station 1", "Phone2");
		lstPeopleExpected.add(peopleExpected1);
		lstPeopleExpected.add(peopleExpected2);
		PersonStationExpected.add(lstPeopleExpected, 2, 0);
		lstPersonStationExpected.add(PersonStationExpected);
		
		List<String> lstAddressFromStation = new ArrayList<String>();
		lstAddressFromStation.add("Address To Station 1");
		
        List<Person> lstPersonFromAdress = new ArrayList<Person>();
        Person person = new Person("FirstName1", "LastName1", "Address To Station 1", "City1", "Zip1", "Phone1", "Mail1");
        lstPersonFromAdress.add(person);
        Person person2 = new Person("FirstName2", "LastName2", "Address To Station 1", "City1", "Zip1", "Phone2", "Mail2");
        lstPersonFromAdress.add(person2);
        
        when(fireStationRepositoryMock.getAddressByStationNumber("1")).thenReturn(lstAddressFromStation);
        when(personRepositoryMock.getPersonByAddress(lstAddressFromStation)).thenReturn(lstPersonFromAdress);
        when(medicalrecordsServiceMock.isAChild(person)).thenReturn(false);
        when(medicalrecordsServiceMock.isAChild(person2)).thenReturn(true);
        
        List<PersonStationNumber> lstResult = fireStationService.getPersonFromStation(lstStationTest);
        
        assertThat(lstResult.get(0).getNumberAdult() == lstPersonStationExpected.get(0).getNumberAdult());
        assertThat(lstResult.get(0).getNumberChild() == lstPersonStationExpected.get(0).getNumberChild());
        assertThat(lstResult.get(0).getPeople().size() == lstPersonStationExpected.get(0).getPeople().size());
        for(int i = 0; i < lstResult.get(0).getPeople().size(); i++)
        {
        	assertThat(lstResult.get(0).getPeople().get(i).getLastName().equals(lstPersonStationExpected.get(0).getPeople().get(i).getLastName()));
        	assertThat(lstResult.get(0).getPeople().get(i).getFirstName().equals(lstPersonStationExpected.get(0).getPeople().get(i).getFirstName()));
        	assertThat(lstResult.get(0).getPeople().get(i).getAddress().equals(lstPersonStationExpected.get(0).getPeople().get(i).getAddress()));
        	assertThat(lstResult.get(0).getPeople().get(i).getPhone().equals(lstPersonStationExpected.get(0).getPeople().get(i).getPhone()));
        }
	}
	
	@Test
    public void getPersonFromStationWithFireStationNotInData() {
		
		List<String> lstStationTest = new ArrayList<String>();
		lstStationTest.add("FireStation Not In Data");
		
		List<String> lstAddressFromStation = new ArrayList<String>();
		lstAddressFromStation.add("Address To Station 1");
		
        List<Person> lstPersonFromAdress = new ArrayList<Person>();
        Person person = new Person("FirstName1", "LastName1", "Address To Station 1", "City1", "Zip1", "Phone1", "Mail1");
        lstPersonFromAdress.add(person);
        Person person2 = new Person("FirstName2", "LastName2", "Address To Station 1", "City1", "Zip1", "Phone2", "Mail2");
        lstPersonFromAdress.add(person2);
        Person person3 = new Person("FirstName3", "LastName3", "Address NOT TO Station 1", "City3", "Zip3", "Phone3", "Mail3");
        lstPersonFromAdress.add(person3);
        
        when(fireStationRepositoryMock.getAddressByStationNumber("FireStation Not In Data")).thenReturn(null);
        when(personRepositoryMock.getPersonByAddress(lstAddressFromStation)).thenReturn(lstPersonFromAdress);
        when(medicalrecordsServiceMock.isAChild(any(Person.class))).thenReturn(false);
        
        assertThat(fireStationService.getPersonFromStation(lstStationTest)).isNull();
	}
	
	@Test
    public void getPhonePersonFromStationWithFireStationNumberInData() {
		
		List<String> lstStationTest = new ArrayList<String>();
		lstStationTest.add("1");
		
		List<String> lstAddressFromStation = new ArrayList<String>();
		lstAddressFromStation.add("Address To Station 1");
		
		List<Person> lstPersonFromAdress = new ArrayList<Person>();
        Person person = new Person("FirstName1", "LastName1", "Address To Station 1", "City1", "Zip1", "Phone1", "Mail1");
        lstPersonFromAdress.add(person);
        Person person2 = new Person("FirstName2", "LastName2", "Address To Station 1", "City1", "Zip1", "Phone2", "Mail2");
        lstPersonFromAdress.add(person2);
        Person person3 = new Person("FirstName3", "LastName3", "Address To Station 1", "City3", "Zip3", "Phone1", "Mail3");
        lstPersonFromAdress.add(person3);
        
        List<String> lstPhoneExpected = new ArrayList<>();
        lstPhoneExpected.add(person.getPhone());
        lstPhoneExpected.add(person2.getPhone());
		
		when(fireStationRepositoryMock.getAddressByStationNumber("1")).thenReturn(lstAddressFromStation);
		when(personRepositoryMock.getPersonByAddress(lstAddressFromStation)).thenReturn(lstPersonFromAdress);
		
		List<String> lstResult = fireStationService.getPhonePersonFromStation(lstStationTest);
		assertThat(lstResult.size() == lstPhoneExpected.size());
		for(int i = 0; i < lstResult.size(); i++)
        	assertThat(lstResult.get(i).equals(lstPhoneExpected.get(i)));
	}
		
		
	
	@Test
    public void getPhonePersonFromStationWithFireStationNumerNotInData() {
		
		List<String> lstStationTest = new ArrayList<String>();
		lstStationTest.add("Station NOT IN DATA");
		
		List<String> lstAddressFromStation = new ArrayList<String>();
		lstAddressFromStation.add("Address To Station 1");
		
		List<Person> lstPersonFromAdress = new ArrayList<Person>();
        Person person = new Person("FirstName1", "LastName1", "Address To Station 1", "City1", "Zip1", "Phone1", "Mail1");
        lstPersonFromAdress.add(person);
        Person person2 = new Person("FirstName2", "LastName2", "Address To Station 1", "City1", "Zip1", "Phone2", "Mail2");
        lstPersonFromAdress.add(person2);
        Person person3 = new Person("FirstName3", "LastName3", "Address To Station 1", "City3", "Zip3", "Phone1", "Mail3");
        lstPersonFromAdress.add(person3);
		
		when(fireStationRepositoryMock.getAddressByStationNumber("Station NOT IN DATA")).thenReturn(null);
		when(personRepositoryMock.getPersonByAddress(lstAddressFromStation)).thenReturn(lstPersonFromAdress);
		
		assertThat(fireStationService.getPersonFromStation(lstStationTest)).isNull();
	}
	
	@Test
    public void getpersonFromAddressWithAdressFireStationInData() {
		
		List<PersonFireAddress> lstResult;
		List<String> lstAddressTest = new ArrayList<String>();
		lstAddressTest.add("Address To Station 1");
		
		List<String> lstAddressFromStation = new ArrayList<String>();
		lstAddressFromStation.add("Address To Station 1");
		int ageMock = 20;
		
		List<Person> lstPersonFromAdress = new ArrayList<Person>();
        Person person = new Person("FirstName1", "LastName1", "Address To Station 1", "City1", "Zip1", "Phone1", "Mail1");
        lstPersonFromAdress.add(person);
        Person person2 = new Person("FirstName2", "LastName2", "Address To Station 1", "City1", "Zip1", "Phone2", "Mail2");
        lstPersonFromAdress.add(person2);
        
        List<FireAddress> lstFireAddrressExpected = new ArrayList<>();
        FireAddress fireAddress1 = new FireAddress("LastName1", ageMock, "Phone1", List.of("Medication1"), List.of("Allergie1"));
        FireAddress fireAddress2 = new FireAddress("LastName2", ageMock, "Phone1", List.of("Medication1"), List.of("Allergie1"));
        lstFireAddrressExpected.add(fireAddress1);
        lstFireAddrressExpected.add(fireAddress2);
        
        List<PersonFireAddress> lstPersonFireAddressExpected = new ArrayList<>();
        PersonFireAddress PersonFireAddressExpected = new PersonFireAddress();
        PersonFireAddressExpected.setFireAddress(lstFireAddrressExpected);
        PersonFireAddressExpected.setStation("1");
        lstPersonFireAddressExpected.add(PersonFireAddressExpected);
        
        when(personRepositoryMock.getPersonByAddress(lstAddressTest)).thenReturn(lstPersonFromAdress);
        when(medicalrecordsServiceMock.getAgeFromPerson(any(Person.class))).thenReturn(ageMock);
        when(medicalrecordsServiceMock.getMedicationFromPerson(any(Person.class))).thenReturn(List.of("Medication1"));
        when(medicalrecordsServiceMock.getAllergieFromPerson(any(Person.class))).thenReturn(List.of("Allergie1"));
        when(fireStationRepositoryMock.getFirestationsFromAddress(any(String.class))).thenReturn("1");
        
        lstResult = fireStationService.getpersonFromAddress(lstAddressTest);
        assertThat(lstResult.get(0).getStation()).isEqualTo(lstPersonFireAddressExpected.get(0).getStation());
        assertThat(lstResult.get(0).getFireAddress().size() == lstPersonFireAddressExpected.get(0).getFireAddress().size());
        for(int i = 0; i < lstResult.get(0).getFireAddress().size(); i++)
        {
        	assertThat(lstResult.get(0).getFireAddress().get(i).getLastName().equals(lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getLastName()));
        	assertThat(lstResult.get(0).getFireAddress().get(i).getAge() == (lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getAge()));
        	assertThat(lstResult.get(0).getFireAddress().get(i).getPhone().equals(lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getPhone()));
        	assertThat(lstResult.get(0).getFireAddress().get(i).getMedications().equals(lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getMedications()));
        	assertThat(lstResult.get(0).getFireAddress().get(i).getAllergies().equals(lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getAllergies()));
        }
	}
	
	@Test
    public void getpersonFromAddressWithAdressFireStationNotInData() {
		
		List<String> lstAddressTest = new ArrayList<String>();
		lstAddressTest.add("Address To Station 1 NOT IN DATA");
		
		List<String> lstAddressFromStation = new ArrayList<String>();
		lstAddressFromStation.add("Address To Station 1");
		
		int ageMock = 20;
		List<Person> lstPersonFromAdress = new ArrayList<Person>();
        Person person = new Person("FirstName1", "LastName1", "Address To Station 1", "City1", "Zip1", "Phone1", "Mail1");
        lstPersonFromAdress.add(person);
        Person person2 = new Person("FirstName2", "LastName2", "Address To Station 1", "City1", "Zip1", "Phone2", "Mail2");
        lstPersonFromAdress.add(person2);
        
        List<FireAddress> lstFireAddrressExpected = new ArrayList<>();
        FireAddress fireAddress1 = new FireAddress("LastName1", ageMock, "Phone1", List.of("Medication1"), List.of("Allergie1"));
        FireAddress fireAddress2 = new FireAddress("LastName2", ageMock, "Phone1", List.of("Medication1"), List.of("Allergie1"));
        lstFireAddrressExpected.add(fireAddress1);
        lstFireAddrressExpected.add(fireAddress2);
        
        List<PersonFireAddress> lstPersonFireAddressExpected = new ArrayList<>();
        PersonFireAddress PersonFireAddressExpected = new PersonFireAddress();
        PersonFireAddressExpected.setFireAddress(lstFireAddrressExpected);
        PersonFireAddressExpected.setStation(null);
        lstPersonFireAddressExpected.add(PersonFireAddressExpected);
        
        when(personRepositoryMock.getPersonByAddress(lstAddressTest)).thenReturn(lstPersonFromAdress);
        when(medicalrecordsServiceMock.getAgeFromPerson(any(Person.class))).thenReturn(ageMock);
        when(medicalrecordsServiceMock.getMedicationFromPerson(any(Person.class))).thenReturn(List.of("Medication1"));
        when(medicalrecordsServiceMock.getAllergieFromPerson(any(Person.class))).thenReturn(List.of("Allergie1"));
        when(fireStationRepositoryMock.getFirestations()).thenReturn(lstfireStation);
        
        List<PersonFireAddress> lstResult = fireStationService.getpersonFromAddress(lstAddressTest);
        assertThat(lstResult.get(0).getStation()).isEqualTo(lstPersonFireAddressExpected.get(0).getStation());
        assertThat(lstResult.get(0).getFireAddress().size() == lstPersonFireAddressExpected.get(0).getFireAddress().size());
        for(int i = 0; i < lstResult.get(0).getFireAddress().size(); i++)
        {
        	assertThat(lstResult.get(0).getFireAddress().get(i).getLastName().equals(lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getLastName()));
        	assertThat(lstResult.get(0).getFireAddress().get(i).getAge() == (lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getAge()));
        	assertThat(lstResult.get(0).getFireAddress().get(i).getPhone().equals(lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getPhone()));
        	assertThat(lstResult.get(0).getFireAddress().get(i).getMedications().equals(lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getMedications()));
        	assertThat(lstResult.get(0).getFireAddress().get(i).getAllergies().equals(lstPersonFireAddressExpected.get(0).getFireAddress().get(i).getAllergies()));
        }
	}
	
	@Test
    public void getpersonFromAddressWithAdressPersonNotInData() {
		
		List<String> lstAddressTest = new ArrayList<String>();
		lstAddressTest.add("Address Person NOT IN DATA");
		   
        when(personRepositoryMock.getPersonByAddress(lstAddressTest)).thenReturn(null);
        
        assertThat(fireStationService.getpersonFromAddress(lstAddressTest)).isNull();
	}
	
}
