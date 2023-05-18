package com.openclassrooms.safetynet.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.openclassrooms.safetynet.model.Medicalrecords;
import com.openclassrooms.safetynet.services.MedicalrecordsService;

@RestController
public class MedicalrecordsController {
	
	@Autowired
	private MedicalrecordsService medicalrecordsService;
	
	private static Logger logger = Logger.getLogger(MedicalrecordsController.class.getName());
	private static FileHandler handler = null;
	
	public MedicalrecordsController(MedicalrecordsService medicalrecordsService) {
		this.medicalrecordsService = medicalrecordsService;
		if (handler == null) {
				handler = ALog.getFileHandler();
				logger.addHandler(handler);
		}
	}

	@GetMapping("/medicalrecords")
	public ResponseEntity<List<Medicalrecords>> getMedicalrecords() throws RuntimeException, IOException {
		
		logger.info("Request - all medicalrecords");
		List<Medicalrecords> medicalrecords = medicalrecordsService.getMedicalrecords();
		if (medicalrecords != null) {
			   logger.info("Response - all medicalrecords = " + medicalrecords);
	           return ResponseEntity.status(HttpStatus.OK).body(medicalrecords);
	       } else {
	    	   logger.log(Level.WARNING, "Response - no medicalrecords");
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
	@PostMapping("/medicalrecords")
    public ResponseEntity<Medicalrecords> addMedicalrecords(@RequestBody Medicalrecords medicalrecord) throws RuntimeException, IOException {
		
		logger.info("Request - post medicalrecords for : " + medicalrecord);
		Medicalrecords createdMedicalrecord = medicalrecordsService.addMedicalrecords(medicalrecord);
		    if (createdMedicalrecord != null) {
		    	logger.info("Response - post medicalrecords OK for : " + medicalrecord);
		        return ResponseEntity.status(HttpStatus.OK).body(createdMedicalrecord);
		    } else {
		    	logger.log(Level.WARNING, "Response - post medicalrecords KO for : " + medicalrecord);
		    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		    }
	}
	
	@PutMapping("/medicalrecords")
	public  ResponseEntity<Medicalrecords> updateMedicalrecords(@RequestBody Medicalrecords medicalrecords) throws RuntimeException, IOException {
		
		logger.info("Request - put medicalrecords for : " + medicalrecords);
		Medicalrecords updateMedicalrecord = medicalrecordsService.updateMedicalrecords(medicalrecords);
	    if (updateMedicalrecord != null) {
	    	logger.info("Response - put medicalrecords OK for : " + medicalrecords);
	        return ResponseEntity.status(HttpStatus.OK).body(updateMedicalrecord);
	    } else {
	    	logger.log(Level.WARNING, "Response - put medicalrecords KO for : " + medicalrecords);
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    } 
	}

	@DeleteMapping("/medicalrecords")
	public  ResponseEntity<Medicalrecords> deleteMedicalrecord(@RequestBody Medicalrecords medicalrecords) throws RuntimeException, IOException {
		
		logger.info("Request - delete medicalrecords for : " + medicalrecords);
		Medicalrecords deleteMedicalrecord = medicalrecordsService.deleteMedicalrecords(medicalrecords);
	    if (deleteMedicalrecord != null) {
	    	logger.info("Request - delete medicalrecords OK for : " + medicalrecords);
	        return ResponseEntity.status(HttpStatus.OK).body(deleteMedicalrecord);
	    } else {
	    	logger.log(Level.WARNING, "Request - delete medicalrecords KO for : " + medicalrecords);
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    }
	}
}
