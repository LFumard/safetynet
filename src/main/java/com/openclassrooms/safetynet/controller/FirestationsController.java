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

import com.openclassrooms.safetynet.model.Firestations;
import com.openclassrooms.safetynet.model.PersonFireAddress;
import com.openclassrooms.safetynet.model.PersonStationNumber;
import com.openclassrooms.safetynet.services.FirestationsService;

@RestController
public class FirestationsController {

	private FirestationsService firestationsService;
	
	private static Logger logger = Logger.getLogger(FirestationsController.class.getName());
	private static FileHandler handler = null;
	
	public FirestationsController(FirestationsService firestationsService) {
		this.firestationsService = firestationsService;
		if (handler == null) {
				handler = ALog.getFileHandler();
				logger.addHandler(handler);
		}
	}
	
	
	@PostMapping("/firestation")
    public ResponseEntity<Firestations> addFireStation(@RequestBody Firestations fireStation) throws RuntimeException, IOException {
		
		logger.info("Request - post fireStation for : " + fireStation);
	    Firestations createdFireStation = firestationsService.addFireStation(fireStation);
	    if (createdFireStation != null) {
	    	logger.info("Response - post fireStation OK for : " + fireStation);
	        return ResponseEntity.status(HttpStatus.OK).body(createdFireStation);
	    } else {
	    	logger.log(Level.WARNING, "Response - post fireStation OK for : " + fireStation);
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    }
	}
	
	 
	@PutMapping("/firestation")
	public  ResponseEntity<Firestations> updateFireStation(@RequestBody Firestations fireStation) throws RuntimeException, IOException {
		
		logger.info("Request - put fireStation for : " + fireStation);
		Firestations updateFireStation = firestationsService.updateFireStation(fireStation);
	    if (updateFireStation != null) {
	    	logger.info("Response - put fireStation OK for : " + fireStation);
	        return ResponseEntity.status(HttpStatus.OK).body(updateFireStation);
	    } else {
	    	logger.log(Level.WARNING, "Response - put fireStation KO for : " + fireStation);
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    } 
	}
	
	@DeleteMapping("/firestation")
	public  ResponseEntity<Firestations> deleteFireStation(@RequestBody Firestations fireStation) throws RuntimeException, IOException {

		logger.info("Request - delete fireStation for : " + fireStation);
	    Firestations deleteFireStation = firestationsService.deleteFireStation(fireStation);
	    if (deleteFireStation != null) {
	    	logger.info("Response - delete fireStation OK for : " + fireStation);
	    	return ResponseEntity.status(HttpStatus.OK).body(deleteFireStation);
	    } else {
	    	logger.log(Level.WARNING, "Response - delete fireStation KO for : " + fireStation);
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    }
	}
	
	@GetMapping("/firestation")
	public ResponseEntity<List<PersonStationNumber>> getPersonFromStation(@RequestParam("station_number") List<String> station_number) throws RuntimeException, IOException {
 
		logger.info("Request - getPersonFromStation for station : " + station_number);
		List<PersonStationNumber> personStationNumber = firestationsService.getPersonFromStation(station_number);
		if (personStationNumber != null) {
			   logger.info("Response - getPersonFromStation for station : " + station_number + " = " + personStationNumber);
	           return ResponseEntity.status(HttpStatus.OK).body(personStationNumber);
	       } else {
	    	   logger.log(Level.WARNING, "Response - getPersonFromStation KO for : " + station_number);
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
	
	@GetMapping("/phoneAlert")
	public ResponseEntity<List<String>> getPhonePersonFromStation(@RequestParam("firestation_number") List<String> firestation_number) throws RuntimeException, IOException {
 
		logger.info("Request - getPhonePersonFromStation for station : " + firestation_number);
		List<String> personPhoneFromStationNumber = firestationsService.getPhonePersonFromStation(firestation_number);
		if (personPhoneFromStationNumber != null) {
			   logger.info("Response - getPhonePersonFromStation for station : " + firestation_number + " = " + personPhoneFromStationNumber);
	           return ResponseEntity.status(HttpStatus.OK).body(personPhoneFromStationNumber);
	       } else {
	    	   logger.log(Level.WARNING, "Response - getPhonePersonFromStation KO for station : " + firestation_number);
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
	@GetMapping("/fire")
	public ResponseEntity<List<PersonFireAddress>> getPersonFromAddress(@RequestParam("address") List<String> address) throws RuntimeException, IOException {

		logger.info("Request - getPersonFromAddress for address station : " + address);
		List<PersonFireAddress> personFromAddress = firestationsService.getpersonFromAddress(address);
		if (personFromAddress != null) {
			   logger.info("Response - getPersonFromAddress for address station : " + address + " = " + personFromAddress);
	           return ResponseEntity.status(HttpStatus.OK).body(personFromAddress);
	       } else {
	    	   logger.log(Level.WARNING, "Response - getPersonFromAddress KO for address station : " + address);
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
}
