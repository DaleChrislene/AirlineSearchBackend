package com.wego.backend.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name= "Route")
@NamedQueries({
	@NamedQuery(
			name= "airlineViewer.entities.Route.findAll",
			query = "SELECT r FROM Route r"
			),
	@NamedQuery(
			name="airlineViewer.entities.Route.findAirlineForRoute",
			query = "SELECT r FROM Route r WHERE ( UPPER(r.from)=:from AND UPPER(r.to)=:to ) OR (UPPER(r.from)=:to AND UPPER(r.to)=:from )"
			)
})
@XmlRootElement(name="route")
public class Route {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	
	
	@Column(name = "from", nullable = false)
	private String from;
	
	
	@Column(name = "to", nullable = false)
	private String to;
	
	
	@Column(name = "airline", nullable = false)
	private String airline;
	
	public Route(){
		
	}
	
	public Route(String from, String to, String airlineName){
		this.from = from;
		this.to = to;
		this.airline = airlineName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airlineName) {
		this.airline = airlineName;
	}
	
	
	/**
	 * By default, Object.equals() returns true
	 * if both refer to the same object (same location in memory)
	 * 
	 * @return
	 * true: if same object/ object values are equal
	 * false: otherwise
	 */
	@Override
	public boolean equals(Object o){
		if(this == o){
			return true;
		}
		
		if(!(o instanceof Route)){
			return false;
		}
		
		final Route route = (Route) o;
		
		return Objects.equals(this.id, route.id) &&
				Objects.equals(this.from, route.from) &&
				Objects.equals(this.to, route.to) &&
				Objects.equals(this.airline, route.airline);
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(id, from, to, airline);
	}
}
