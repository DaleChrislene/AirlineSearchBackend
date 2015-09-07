//package com.wego.backend.entities.dao;
//
//import io.dropwizard.hibernate.AbstractDAO;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import jersey.repackaged.com.google.common.base.Optional;
//
//import org.hibernate.SessionFactory;
//
//import com.wego.backend.entities.Airline;
//import com.wego.backend.entities.Route;
//
//public class AirlineDAO extends AbstractDAO<Airline>{
//	
//	private static final String PARAM_FROM = "from";
//	private static final String PARAM_TO = "to";
//	
//	public AirlineDAO(SessionFactory sessionFactory) {
//		super(sessionFactory);
//	}
//	
//	public Optional<Airline> findById(Long id){
//		return Optional.fromNullable(get(id));
//	}
//	
////	public List<Route> findAll(){
////		return list(namedQuery("airlineViewer.entities.Route.findAll"));
////	}
//	
//	public List<Airline> findAirlineForRoute(Route route){
//		List<Airline> result = new ArrayList<Airline>();
//		List<Route> temp = list(namedQuery("airlineViewer.entities.Route.findAirlineForRoute")
//				.setParameter(PARAM_FROM, route.getFrom().trim())
//				.setParameter(PARAM_TO, route.getTo().trim())); 
//		return null;
//	}	
//}
