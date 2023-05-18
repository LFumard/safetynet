package com.openclassrooms.safetynet.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.openclassrooms.safetynet.model.DataSource;
import com.openclassrooms.safetynet.model.Firestations;

@Component
public class FireStationRepository {
	
	private DataSource dataSource;

	public FireStationRepository(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public List<Firestations> getFirestations() {
		return dataSource.getFirestations();
	}

	public boolean add(Firestations fireStation) {
		return(dataSource.firestations.add(fireStation));
	}

	public Firestations updateFirestations(int ifireStation, Firestations fireStation) {
		return(dataSource.firestations.set(ifireStation, fireStation));
	}

	public boolean remove(Firestations fireStation) {
		return(dataSource.firestations.remove(fireStation));
	}

	public List<String> getAddressByStationNumber(String station_number) {
		List<String> lstAddressFromStation = new ArrayList<>();
		for(int i = 0; i < dataSource.firestations.size(); i++) {
			if(dataSource.firestations.get(i).getStation().equals(station_number)) {
				lstAddressFromStation.add(dataSource.firestations.get(i).getAddress());
			}
		}
		if(lstAddressFromStation.size() > 0)
			return lstAddressFromStation;
		else return null;
	}

	public String getFirestationsFromAddress(String address) {
		for(int i = 0; i < dataSource.firestations.size(); i++) {
			if(dataSource.firestations.get(i).getAddress().equals(address))
				return dataSource.firestations.get(i).getStation();
		}
		return null;
	}
}
