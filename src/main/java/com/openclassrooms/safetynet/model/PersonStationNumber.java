package com.openclassrooms.safetynet.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PersonStationNumber {
	private List<People> people = new ArrayList<People>();
	private int numberAdult;
	private int numberChild;
	
	public int getNumberAdult() {
		return numberAdult;
	}

	public void setNumberAdult(int numberAdult) {
		this.numberAdult = numberAdult;
	}

	public int getNumberChild() {
		return numberChild;
	}

	public void setNumberChild(int numberChild) {
		this.numberChild = numberChild;
	}

	public List<People> getPeople() {
		return people;
	}

	public void setPeople(List<People> people) {
		this.people = people;
	}

	public void add(List<People> people, int numberAdult, int numberChild) {
		this.people = people;
		this.numberAdult = numberAdult;
		this.numberChild = numberChild;
	}
	
}
