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

import com.openclassrooms.safetynet.model.Firestations;
import com.openclassrooms.safetynet.model.PersonFireAddress;
import com.openclassrooms.safetynet.model.PersonStationNumber;
import com.openclassrooms.safetynet.services.FirestationsService;

@RestController
public class FirestationsController {

	private FirestationsService firestationsService;
	
	public FirestationsController(FirestationsService firestationsService) {
		this.firestationsService = firestationsService;
	}
	
	@PostMapping("/firestation")
    public ResponseEntity<Firestations> addFireStation(@RequestBody Firestations fireStation) {
	    Firestations createdFireStation = firestationsService.addFireStation(fireStation);
	    if (createdFireStation != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(createdFireStation);
	    } else {
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    }
	}
	
	 
	@PutMapping("/firestation")
	public  ResponseEntity<Firestations> updateFireStation(@RequestBody Firestations fireStation) {
		Firestations updateFireStation = firestationsService.updateFireStation(fireStation);
	    if (updateFireStation != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(updateFireStation);
	    } else {
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    } 
	}
	
	@DeleteMapping("/firestation")
	public  ResponseEntity<Firestations> deleteFireStation(@RequestBody Firestations fireStation) {
	    Firestations deleteFireStation = firestationsService.deleteFireStation(fireStation);
	    if (deleteFireStation != null) {
	    	return ResponseEntity.status(HttpStatus.OK).body(deleteFireStation);
	    } else {
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    }
	}
	
	@GetMapping("/firestation")
	public ResponseEntity<List<PersonStationNumber>> getPersonFromStation(@RequestParam("station_number") List<String> station_number) {
		
		System.out.println("station_number :"+station_number+":");
		List<PersonStationNumber> personStationNumber = firestationsService.getPersonFromStation(station_number);
		if (personStationNumber != null) {
	           return ResponseEntity.status(HttpStatus.OK).body(personStationNumber);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
	
	@GetMapping("/phoneAlert")
	public ResponseEntity<List<String>> getPhonePersonFromStation(@RequestParam("firestation_number") List<String> firestation_number) {
		List<String> personPhoneFromStationNumber = firestationsService.getPhonePersonFromStation(firestation_number);
		if (personPhoneFromStationNumber != null) {
	           return ResponseEntity.status(HttpStatus.OK).body(personPhoneFromStationNumber);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
	@GetMapping("/fire")
	public ResponseEntity<List<PersonFireAddress>> getPersonFromAddress(@RequestParam("address") List<String> address) {
		List<PersonFireAddress> personFromAddress = firestationsService.getpersonFromAddress(address);
		if (personFromAddress != null) {
	           return ResponseEntity.status(HttpStatus.OK).body(personFromAddress);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
}
