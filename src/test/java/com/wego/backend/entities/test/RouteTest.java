package com.wego.backend.entities.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Test;

import com.wego.backend.entities.Route;

public class RouteTest {
	
	private static final String SINGLE_ROUTE_PATH = "src/test/resources/fixtures/route.xml";
	private static final String EMPTY_ROUTE_LIST_PATH = "src/test/resources/fixtures/empty_route_list.xml";
	private static final String MULTI_ROUTE__LIST_PATH = "src/test/resources/fixtures/multi_route_list.xml";
	
	@XmlRootElement(name = "routes")
	@XmlAccessorType (XmlAccessType.FIELD)
	static public class RouteList
	{
	    @XmlElement(name = "route")
	    private List<Route> routes = null;
	 
	    public List<Route> getRoutes() {
	        return routes;
	    }
	 
	    public void setRoutes(List<Route> routes) {
	        this.routes = routes;
	    }
	}
	
	static RouteList routes;
	
	@Test
	/*
	 * Test de-serialization from XML when object is complete
	 */
    public void deserializesFromXML_allFields() throws Exception {
        final Route route = new Route("Singapore", "Tokyo", "Cathay Pacific");
        File file = new File(SINGLE_ROUTE_PATH);
        JAXBContext jaxbContext = JAXBContext.newInstance(Route.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Route r = (Route) jaxbUnmarshaller.unmarshal(file);        
        assertThat(r).isEqualToComparingOnlyGivenFields(route, "from","to","airline");
    }
	
	@Test
	/*
	 * Testing common use-case -> Receive locations {from, to}
	 */
	public void deserializesFromXML_partial() throws Exception {
        final Route route = new Route();
        route.setFrom("Singapore");
        route.setTo("Tokyo");
        
        File file = new File(SINGLE_ROUTE_PATH);
        JAXBContext jaxbContext = JAXBContext.newInstance(Route.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Route r = (Route) jaxbUnmarshaller.unmarshal(file);        
        assertThat(r).isEqualToComparingOnlyGivenFields(route, "from","to");
    }
	
	@Test
	/*
	 * Single Route object as result
	 */
	public void serializesToXML() throws Exception{
		final Route route = new Route("Singapore","Tokyo","Cathay Pacific");
		JAXBContext jaxbContext = JAXBContext.newInstance(Route.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		StringWriter sw = new StringWriter();;
		jaxbMarshaller.marshal(route, sw);

		String routeFromFixture = getFileAsString(SINGLE_ROUTE_PATH);
		assertThat(sw.toString()).isEqualTo(routeFromFixture);
	}
	
	@Test
	/*
	 * List of Route objects as result
	 */
	public void serializesToXML_2() throws Exception{
		routes = new RouteList();
		
		final Route route1 = new Route("Singapore","Tokyo","Cathay Pacific");
		final Route route2 = new Route("Singapore","London","Cathay Pacific");
		final Route route3 = new Route("Dubai","Tokyo","Cathay Pacific");
		
		List<Route> resultList = new ArrayList<Route>();
		resultList.add(route1);
		resultList.add(route2);
		resultList.add(route3);
		
		routes.setRoutes(resultList);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RouteList.class);
		
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		java.io.StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(routes, sw);

		String routeFromFixture = getFileAsString(MULTI_ROUTE__LIST_PATH);
		assertThat(sw.toString()).isEqualTo(routeFromFixture);
		
	}
	
	@Test
	/*
	 * Test Empty List as result
	 */
	public void serializesToXML_3() throws Exception{
		routes = new RouteList();
		List<Route> resultList = new ArrayList<Route>();
		
		JAXBContext jaxbContext = JAXBContext.newInstance(RouteList.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		
		routes.setRoutes(resultList);
		
		StringWriter sw = new StringWriter();;
		jaxbMarshaller.marshal(routes, sw);

		String routeFromFixture = getFileAsString(EMPTY_ROUTE_LIST_PATH);
		assertThat(sw.toString()).isEqualTo(routeFromFixture);
		
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
