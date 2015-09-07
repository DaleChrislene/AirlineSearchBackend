package com.wego.backend.entities.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jersey.repackaged.com.google.common.base.Optional;

import org.hibernate.SessionFactory;

import com.wego.backend.entities.Route;

public class RouteDAO extends AbstractDAO<Route>{
	
	private static final String PARAM_FROM = "from";
	private static final String PARAM_TO = "to";
	
	public RouteDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
	
	public Optional<Route> findById(Long id){
		return Optional.fromNullable(get(id));
	}
	
	public List<Route> findAll(){
		return list(namedQuery("airlineViewer.entities.Route.findAll"));
	}
	
	public List<Route> findAirlineForRoute(Route route){
		return list(namedQuery("airlineViewer.entities.Route.findAirlineForRoute")
				.setParameter(PARAM_FROM, route.getFrom().trim().toUpperCase())
				.setParameter(PARAM_TO, route.getTo().trim().toUpperCase()));
	}	
}
