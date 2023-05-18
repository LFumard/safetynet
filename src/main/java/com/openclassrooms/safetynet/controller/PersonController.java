package com.openclassrooms.safetynet.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;

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
	
	private static Logger logger = Logger.getLogger(PersonController.class.getName());
	
	private static FileHandler handler = null;
	
	public PersonController(PersonServiceImpl personService) {
		this.personService = personService;
		if (handler == null) {
				handler = ALog.getFileHandler();
				logger.addHandler(handler);
		}
	}
	
	@GetMapping("/personInfo")
	public ResponseEntity<List<PersonInfo>> getPersonInfo(@RequestParam("firstName") String firstName, @RequestParam("lastName")String lastName) throws RuntimeException, IOException {
		
		logger.info("Request - Person info with firstname = " + firstName + ", lastname=" + lastName);
		List<PersonInfo> personInfo = personService.getPersonInfo(firstName, lastName);
		if (personInfo != null) {
			logger.info("Response - personInfo for " + firstName + " " + lastName + "=" + personInfo);
			return ResponseEntity.status(HttpStatus.OK).body(personInfo);
	       } else {
	    	   logger.log(Level.WARNING, "Response - Wrong entry - personInfo : " + firstName + " " + lastName);
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
	@GetMapping("/childAlert")
	public ResponseEntity<List<ChildAdress>> getChildFromAddress(@RequestParam("address") String address) throws RuntimeException, IOException {
		
		logger.info("Request - getChildFromAddress with address : " + address);
		List<ChildAdress> personInfo = personService.getChildFromAddress(address);
		if (personInfo != null) {
			   logger.info("Response - getChildFromAddress with address :  " + address + "=" + personInfo);
	           return ResponseEntity.status(HttpStatus.OK).body(personInfo);
	       } else {
	    	   logger.log(Level.WARNING, "Response - Wrong entry - getChildFromAddress with address : " + address);
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}

	@PostMapping("/person")
	public ResponseEntity<Person> addPerson(@RequestBody Person person) throws RuntimeException, IOException {
		
		logger.info("Request - addPerson : " + person);
		Person updatedPerson =  personService.addPerson(person);
		if (updatedPerson != null) {
			   logger.info("Response - addPerson OK: " + person);
	           return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
	       } else {
	    	   logger.log(Level.WARNING, "Response - addPerson KO: " + person);
	    	   return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	       }
	}
	
	@PutMapping("/person")
	 public ResponseEntity<Person> updatePerson(@RequestBody Person person) throws RuntimeException, IOException {
		
		logger.info("Request - updatePerson : " + person);
		Person updatedPerson = personService.updatePerson(person);
		if (updatedPerson != null) {
			   logger.info("Response - updatePerson OK: " + person);
	           return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
	       } else {
	    	   logger.log(Level.WARNING, "Response - updatePerson KO: " + person);
	    	   return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	       }
	}
	
	@DeleteMapping("/person")
	public ResponseEntity<Person> deletePerson(@RequestParam("firstName") String firstName, @RequestParam("lastName")String lastName) throws RuntimeException, IOException {
		
		logger.info("Request - deletePerson : " + firstName + " " + lastName);
		Person updatedPerson = personService.deletePerson(firstName, lastName);
		if (updatedPerson != null) {
			   logger.info("Response - deletePerson OK: " +  firstName + " " + lastName);
	           return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
	       } else {
	    	   logger.log(Level.WARNING, "Response - deletePerson KO: " + firstName + " " + lastName);
	    	   return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	       }
	}
	
	@GetMapping("/communityEmail")
	public ResponseEntity<List<String>> getCommunityEmail(@RequestParam("city") String city) throws RuntimeException, IOException {
		
		logger.info("Request - getCommunityEmail with city : " + city);
		List<String> emailList = personService.getCommunityEmail(city);
		if (emailList != null) {
			logger.info("Response - getCommunityEmail for " + city + "=" + emailList);
			return ResponseEntity.status(HttpStatus.OK).body(emailList);
	       } else {
	    	   logger.log(Level.WARNING, "ResponseWrong entry - communityEmail with city : " + city);
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
}
 