package unitn.dallatorre.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import unitn.dallatorre.entities.People;
import unitn.dallatorre.entities.Person;

public class MainTest extends ClientEvaluation {
	private static final String ADDRESS = "http://localhost:8080/SDE_2_SERVER";

	private static int first_person_id, last_person_id;
	private static Person first_person;
	
	public static void main(String[] argv) throws Exception {
		System.out.println("SERVER URL : " + ADDRESS);
		System.out.println();
		requestNo01_checkNumberOfPeople();
		requestNo02_CheckFirstPeopleExists();
		requestNo03_ChangeFirstPersonName();
	}

	private static void requestNo01_checkNumberOfPeople() throws Exception {
		WebTarget webTarget = generateWebTarget("/person");
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.get();
		response.bufferEntity();
		
		People people = response.readEntity(People.class);
		List<Person> listOfPersons = people.getPersons();

		first_person_id = listOfPersons.get(0).getId();
		last_person_id = listOfPersons.get(listOfPersons.size()-1).getId();
		printResponse(response, webTarget, 1,"GET","", listOfPersons.size()>=5);
	}
	
	private static void requestNo02_CheckFirstPeopleExists() throws Exception {
		WebTarget webTarget = generateWebTarget("/person/"+first_person_id);
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.get();
		response.bufferEntity();
		
		first_person = response.readEntity(Person.class);
		
		printResponse(response, webTarget, 2, "GET","", response.getStatus()==200);
	}
	
	private static void requestNo03_ChangeFirstPersonName() throws Exception {
		WebTarget webTarget = generateWebTarget("/person/"+first_person_id);
		
		String previousName = first_person.getFirstname();
		first_person.setFirstname(first_person.getFirstname()+"_EDIT");
		first_person.setActivitypreference(null);
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.put(Entity.entity(first_person, MediaType.APPLICATION_XML));
		response.bufferEntity();
		
		first_person = response.readEntity(Person.class);
		
		printResponse(response, webTarget, 3, "PUT", "APPLICATION_XML", first_person.getFirstname().equals(previousName+"_EDIT"));
	}
	
	private static WebTarget generateWebTarget(String target) {
		Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
		WebTarget webTarget = client.target(ADDRESS).path(target);
		return webTarget;
	}
}
