package com.openclassrooms.safetynet.model;

import org.springframework.stereotype.Component;

@Component
public class Firestations {
	private String address;
	private String station;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == this) 
			return true;
		return(this.toString().equals(obj.toString()));
	}
	@Override
	public String toString() {
		return "{" +
		        " address='" + address + '\'' +
		        ", station='" + station + '\'' +
		        '}';
	}
	
}
