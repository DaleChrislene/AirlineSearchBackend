package com.wego.backend.entities;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="airline")
public class Airline {

	String airlineName;
	
	public Airline(){
		
	}
	
	public Airline(String airline){
		this.airlineName = airline;
	}
	
	public String getAirlineName(){
		return this.airlineName;
	}
	
	@XmlElement
	public void setAirlineName(String airline){
		this.airlineName = airline;
	}	
	
	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		
		if(!(o instanceof Route)){
			return false;
		}
		
		final Airline airline = (Airline) o;
		
		return 
				Objects.equals(this.airlineName, airline.airlineName);
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(airlineName);
	}
}
