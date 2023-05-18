package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.model.Firestations;
import com.openclassrooms.safetynet.model.PersonFireAddress;
import com.openclassrooms.safetynet.model.PersonStationNumber;

public interface FirestationsService {

	public Firestations addFireStation(Firestations firestations);
	public Firestations updateFireStation(Firestations fireStation);
	public Firestations deleteFireStation(Firestations fireStation);
	public List<PersonStationNumber> getPersonFromStation(List<String> station_number);
	public List<String> getPhonePersonFromStation(List<String> firestation_number);
	public List<PersonFireAddress> getpersonFromAddress(List<String> address);
}
