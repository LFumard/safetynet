package com.openclassrooms.safetynet.model;

import java.util.ArrayList;
import java.util.List;

public class PersonFireAddress {
	private List<FireAddress> fireAddress = new ArrayList<FireAddress>();
	private String station;
	
	public List<FireAddress> getFireAddress() {
		return fireAddress;
	}
	public void setFireAddress(List<FireAddress> fireAddress) {
		this.fireAddress = fireAddress;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
}
