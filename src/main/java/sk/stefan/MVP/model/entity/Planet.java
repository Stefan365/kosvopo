package sk.stefan.MVP.model.entity;

import java.io.Serializable;

public class Planet implements Serializable {
	
	String name;
	
	public Planet(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
