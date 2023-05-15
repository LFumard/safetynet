package com.openclassrooms.safetynet.controller;

import java.util.List;

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
	
	public MedicalrecordsController(MedicalrecordsService medicalrecordsService) {
		this.medicalrecordsService = medicalrecordsService;
	}

	@GetMapping("/medicalrecords")
	public ResponseEntity<List<Medicalrecords>> getMedicalrecords() {
		List<Medicalrecords> medicalrecords = medicalrecordsService.getMedicalrecords();
		if (medicalrecords != null) {
	           return ResponseEntity.status(HttpStatus.OK).body(medicalrecords);
	       } else {
	    	   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	       }
	}
	
	@PostMapping("/medicalrecords")
    public ResponseEntity<Medicalrecords> addMedicalrecords(@RequestBody Medicalrecords medicalrecord) {
		Medicalrecords createdMedicalrecord = medicalrecordsService.addMedicalrecords(medicalrecord);
		    if (createdMedicalrecord != null) {
		        return ResponseEntity.status(HttpStatus.OK).body(createdMedicalrecord);
		    } else {
		    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		    }
	}
	
	@PutMapping("/medicalrecords")
	public  ResponseEntity<Medicalrecords> updateMedicalrecords(@RequestBody Medicalrecords medicalrecords) {
		Medicalrecords updateMedicalrecord = medicalrecordsService.updateMedicalrecords(medicalrecords);
	    if (updateMedicalrecord != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(updateMedicalrecord);
	    } else {
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    } 
	}

	@DeleteMapping("/medicalrecords")
	public  ResponseEntity<Medicalrecords> deleteMedicalrecord(@RequestBody Medicalrecords medicalrecords) {
		Medicalrecords deleteMedicalrecord = medicalrecordsService.deleteMedicalrecords(medicalrecords);
	    if (deleteMedicalrecord != null) {
	        return ResponseEntity.status(HttpStatus.OK).body(deleteMedicalrecord);
	    } else {
	    	return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
	    }
	}
}
