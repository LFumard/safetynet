package com.openclassrooms.safetynet.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.openclassrooms.safetynet.model.Medicalrecords;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;

@SpringBootTest()
public class MedicalrecordsServiceTest {

	@MockBean
	private MedicalRecordRepository medicalRecordRepositoryMock;
	
	@Autowired
	private MedicalrecordsService medicalrecordsService;
	
	private Medicalrecords medicalRecords;
	private List<Medicalrecords> lstMedicalRecords;

	@BeforeEach
    public void setUpEachTest() {

		medicalRecords = new Medicalrecords("firstName1", "lastName1", "03/06/1984", List.of("medication1", "medication2"), List.of("allergie1"));
		lstMedicalRecords = new ArrayList<>();
		lstMedicalRecords.add(medicalRecords);
    }
	
	@Test
    public void addMedicalrecordsPrevouslyNotInData() {
		
		Medicalrecords medicalrecordsTest = new Medicalrecords("firstName1 NOT IN DATA", "lastName1", "03/07/2004", List.of("medication1", "medication2"), List.of("allergie1", "allergie2"));
				
		when(medicalRecordRepositoryMock.addMedicalrecords(medicalrecordsTest)).thenReturn(true);
			
		assertThat(medicalrecordsService.addMedicalrecords(medicalrecordsTest)).isEqualTo(medicalrecordsTest);
		verify(medicalRecordRepositoryMock, Mockito.times(1)).addMedicalrecords(medicalrecordsTest);
	}
	
	@Test
    public void addMedicalrecordsPrevouslyInData() {
		
		Medicalrecords medicalrecordsTest = new Medicalrecords("firstName1", "lastName1", "03/07/2004", List.of("medication1", "medication2"), List.of("allergie1", "allergie2"));
				
		when(medicalRecordRepositoryMock.addMedicalrecords(medicalrecordsTest)).thenReturn(false);
			
		assertThat(medicalrecordsService.addMedicalrecords(medicalrecordsTest)).isNull();
	}
	
	@Test
    public void updateMedicalrecordsPrevouslyInData() {
		
		Medicalrecords medicalrecordsTest = new Medicalrecords("firstName1", "lastName1", "03/07/1984", List.of("medication1", "medication2", "NEW medication"), List.of("allergie1", "NEW allergie"));
		
		when(medicalRecordRepositoryMock.updateMedicalrecords(medicalrecordsTest)).thenReturn(true);
		
		assertThat(medicalrecordsService.updateMedicalrecords(medicalrecordsTest).equals(medicalrecordsTest));
		verify(medicalRecordRepositoryMock, Mockito.times(1)).updateMedicalrecords(medicalrecordsTest);
	}
	
	@Test
    public void updateMedicalrecordsPrevouslyNotInData() {
		
		Medicalrecords medicalrecordsTest = new Medicalrecords("firstName1 NOT IN DATA", "lastName1", "03/07/1984", List.of("medication1", "medication2", "NEW medication"), List.of("allergie1", "NEW allergie"));
				
		when(medicalRecordRepositoryMock.updateMedicalrecords(medicalrecordsTest)).thenReturn(false);
			
		assertThat(medicalrecordsService.updateMedicalrecords(medicalrecordsTest)).isNull();
	}
	
	@Test
    public void deleteMedicalrecordsPrevouslyInData() {
		Medicalrecords medicalrecordsTest = new Medicalrecords("firstName1", "lastName1", "03/06/1984", List.of("medication1", "medication2"), List.of("allergie1"));
				
		when(medicalRecordRepositoryMock.deleteMedicalrecords(medicalrecordsTest)).thenReturn(true);
				
		assertThat(medicalrecordsService.deleteMedicalrecords(medicalrecordsTest).equals(medicalrecordsTest));
		verify(medicalRecordRepositoryMock, Mockito.times(1)).deleteMedicalrecords(medicalrecordsTest);
	}
	
	@Test
    public void deleteMedicalrecordsPrevouslyNotInData() {
		Medicalrecords medicalrecordsTest = new Medicalrecords("firstName NOT IN DATA", "lastName1", "03/06/1984", List.of("medication1", "medication2"), List.of("allergie1"));
				
		when(medicalRecordRepositoryMock.deleteMedicalrecords(medicalrecordsTest)).thenReturn(false);
				
		assertThat(medicalrecordsService.deleteMedicalrecords(medicalrecordsTest)).isNull();
	}
	
	
	@Test
    public void isAChildWithAPersonChild() {
		
		LocalDate mockDate = LocalDate.now().minusYears(10); // Enfant de 10 ans au jour du test
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.FRANCE);
		String dateBirthdayMock = mockDate.format(formatter);
		
		Person personTestChild = new Person("FirstName1", "LastName1", "Address To Station 1", "City1", "Zip1", "Phone1", "Mail1");
		
		when(medicalRecordRepositoryMock.getBirthdateFromPerson(personTestChild)).thenReturn(dateBirthdayMock);
		
		assertThat(medicalrecordsService.isAChild(personTestChild)).isTrue();
	}
	
	@Test
    public void isAChildWithAPersonAdult() {
		
		LocalDate mockDate = LocalDate.now().minusYears(30); // Adulte de 30 ans au jour du test
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.FRANCE);
		String dateBirthdayMock = mockDate.format(formatter);
		
		Person personTestChild = new Person("FirstName1", "LastName1", "Address To Station 1", "City1", "Zip1", "Phone1", "Mail1");
		
		when(medicalRecordRepositoryMock.getBirthdateFromPerson(personTestChild)).thenReturn(dateBirthdayMock);
		
		assertThat(medicalrecordsService.isAChild(personTestChild)).isFalse();
	}
	
	@Test
    public void getAgeFromBirthdateWithDefaultDateFormat() throws Exception {
		
		assertThat(medicalrecordsService.getAgeFromBirthdate("125456") == 0);
	}
}
