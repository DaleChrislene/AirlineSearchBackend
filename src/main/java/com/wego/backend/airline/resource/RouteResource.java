package com.wego.backend.airline.resource;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.wego.backend.entities.Airline;
import com.wego.backend.entities.Route;
import com.wego.backend.entities.dao.RouteDAO;

@Path("/")
public class RouteResource {
	private final RouteDAO routeDao;
	
	public RouteResource(RouteDAO routeDao ){
		this.routeDao = routeDao;
	}
	
	@GET
	@Produces("text/html")
	public String getDefaultResponse(){
		String response = "Welcome to the Backend App!";
		return response;
	}
	
	@GET
	@Path("/routes")
	@UnitOfWork
	@Produces(MediaType.APPLICATION_XML)
	public List<Route> getRoute(){
		return routeDao.findAll();
	}
	
	@POST
	@Path("/routes/search")
	@UnitOfWork
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public List<Airline> getAirlinesList(Route rQuery){
		List<Route> routeResultList = routeDao.findAirlineForRoute(rQuery);
		List<Airline> result = populateAirlinesList(routeResultList);
		return result;
	}
	
	/**
	 * Extracts Airlines from the result set of routes
	 * that satisfy request
	 * 
	 * @param routeList - list satisfying route locations in query
	 * @return airlineList - list of airlines from routeList
	 */
	private List<Airline> populateAirlinesList(List<Route> routeList){
		List<Airline> airlineList = new ArrayList<Airline>();
		for(Route r : routeList){
			Airline temp = new Airline();
			temp.setAirlineName(r.getAirline());
			airlineList.add(temp);
		}
		
		return airlineList;
	}
	
}
