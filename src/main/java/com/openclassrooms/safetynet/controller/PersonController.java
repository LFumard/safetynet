package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.ChildAdress;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonInfo;
import com.openclassrooms.safetynet.services.PersonServiceImpl;


@RestController
public class PersonController {
	
	private PersonServiceImpl personService;
	
	public PersonController(PersonServiceImpl personService) {
		this.personService = personService;
	}
	
	@GetMapping("/personInfo")
	public ResponseEntity<List<PersonInfo>> getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName")String lastName) {
		List<PersonInfo> personInfo = personService.getPersonInfo(firstName, lastName);
		if (personInfo != null) {
			return ResponseEntity.status(HttpStatus.OK).body(personInfo);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
	@GetMapping("/childAlert")
	public ResponseEntity<List<ChildAdress>> getChildFromAddress(@RequestParam("address") String address) {
		List<ChildAdress> personInfo = personService.getChildFromAddress(address);
		if (personInfo != null) {
	           return ResponseEntity.status(HttpStatus.OK).body(personInfo);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}

	@PostMapping("/person")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) {
		Person updatedPerson =  personService.addPerson(person);
		if (updatedPerson != null) {
	           return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	       }
	}
	
	@PutMapping("/person")
	 public ResponseEntity<Person> updatePerson(@RequestBody Person person) {
		Person updatedPerson = personService.updatePerson(person);
		if (updatedPerson != null) {
	           return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	       }
	}
	
	@DeleteMapping("/person")
	public ResponseEntity<Person> deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName")String lastName) {
		Person updatedPerson = personService.deletePerson(firstName, lastName);
		if (updatedPerson != null) {
	           return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	       }
	}
	
	@GetMapping("/communityEmail")
	public ResponseEntity<List<String>> getCommunityEmail(@RequestParam("city") String city) {
		List<String> emailList = personService.getCommunityEmail(city);
		if (emailList != null) {
			return ResponseEntity.status(HttpStatus.OK).body(emailList);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
}
 