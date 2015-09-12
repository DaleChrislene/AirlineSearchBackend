package com.wego.backend.airline.resource.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Entity;

import org.junit.Rule;
import org.junit.Test;

import com.wego.backend.airline.resource.RouteResource;
import com.wego.backend.entities.Route;
import com.wego.backend.entities.dao.RouteDAO;

import static org.mockito.Mockito.*;

public class RouteResourceEndPointTest {
	
	private static final String MULTI_ROUTE__LIST_PATH = "src/test/resources/fixtures/multi_route_list.xml";
	private static final String SINGLE_AIRLINE_RESULT_FILE_PATH = "src/test/resources/fixtures/single_airline_result.xml";
	private static final String EMPTY_AIRLINE_RESULT_FILE_PATH = "src/test/resources/fixtures/empty_airline_result.xml";
	
	private static final String GET_ALL_API = "/routes";
	private static final String SEARCH_API = "/routes/search";
	
	private static final RouteDAO DAO = mock(RouteDAO.class);
	
    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new RouteResource(DAO))
            .build();

    @Test
    /*
     * Test if "/routes"
     * responds with all routes
     * in XML format
     * 
     */
    public void getAllRoutes() throws IOException {
    	final Route route1 = new Route("Singapore","Tokyo","Cathay Pacific");
		final Route route2 = new Route("Singapore","London","Cathay Pacific");
		final Route route3 = new Route("Dubai","Tokyo","Cathay Pacific");
		

    	List<Route> routeList = new ArrayList<Route>();
		
    	routeList.add(route1);
    	routeList.add(route2);
    	routeList.add(route3);

    	when(DAO.findAll()).thenReturn(routeList);
    	
        Object resp = resources.client().target(GET_ALL_API)
                .request().get(String.class);
        
        String routeFromFixture = getFileAsString(MULTI_ROUTE__LIST_PATH);
		assertThat(resp.toString()).isEqualTo(routeFromFixture);
    }
    
    @Test
    /*
     * Testing for valid query params with single result
     */
    public void getAirlinesForRoute() throws IOException {
    	final Route routeQuery = new Route("Singapore","Tokyo","");		
    	
    	final Route routeReponse = new Route("Singapore","Tokyo","Cathay Pacific");    	
    	List<Route> routeList = new ArrayList<Route>();		
    	routeList.add(routeReponse);
    	
    	when(DAO.findAirlineForRoute(routeQuery)).thenReturn(routeList);
    	
        Object resp = resources.client().target(SEARCH_API)
                .request().post(Entity.xml(routeQuery), String.class);
        
        String airlineResult = getFileAsString(SINGLE_AIRLINE_RESULT_FILE_PATH);
		assertThat(resp.toString()).isEqualTo(airlineResult);
    }
    
    @Test
    /*
     * Testing for valid query params with no result
     */
    public void getEmptyAirlineList() throws IOException {
    	final Route routeQuery = new Route("Singapore","Tokyo","");		
    	   	
    	List<Route> routeList = new ArrayList<Route>();		
    	
    	when(DAO.findAirlineForRoute(routeQuery)).thenReturn(routeList);
    	
        Object resp = resources.client().target(SEARCH_API)
                .request().post(Entity.xml(routeQuery), String.class);

        String airlineResult = getFileAsString(EMPTY_AIRLINE_RESULT_FILE_PATH);
		assertThat(resp.toString()).isEqualTo(airlineResult);
    }
    
    @Test
    /*
     * Testing for invalid query params with no result
     */
    public void getEmptyAirlineList2() throws IOException {
    	final Route routeQuery = new Route("Singapore","","");		
    	   	
    	List<Route> routeList = new ArrayList<Route>();		
    	
    	when(DAO.findAirlineForRoute(routeQuery)).thenReturn(routeList);
    	
        Object resp = resources.client().target(SEARCH_API)
                .request().post(Entity.xml(routeQuery), String.class);

        String airlineResult = getFileAsString(EMPTY_AIRLINE_RESULT_FILE_PATH);
		assertThat(resp.toString()).isEqualTo(airlineResult);
    }
    
    /*
	 * Get contents of file into a single string
	 */
	public String getFileAsString(String path) throws IOException{
		String fileAsString = "";
		
		File f = new File(path);
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		String line = br.readLine();
		while(line != null){
			fileAsString += line.trim();
			line = br.readLine();
		}
		
		br.close();
		return fileAsString;
	}
}