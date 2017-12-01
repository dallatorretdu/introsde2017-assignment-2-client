package unitn.dallatorre.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import unitn.dallatorre.entities.Activity;
import unitn.dallatorre.entities.ActivityType;
import unitn.dallatorre.entities.ActivityTypesWrapper;
import unitn.dallatorre.entities.ActivityWrapper;
import unitn.dallatorre.entities.People;
import unitn.dallatorre.entities.Person;

public class MainTest extends ClientEvaluation {
	private static final String ADDRESS = "http://localhost:8080/SDE_2_SERVER";
	private static String MEDIA_TYPE = MediaType.APPLICATION_XML;

	private static int first_person_id, last_person_id, activity_id;
	private static Person first_person, inserted_person;
	private static ArrayList<String> activity_types;
	private static String activity_type; 
	private static Activity created_activity;
	
	public static void main(String[] argv) throws Exception {
		System.out.println("SERVER URL : " + ADDRESS);
		System.out.println("SENDING XML TESTS");
		System.out.println();
		MEDIA_TYPE = MediaType.APPLICATION_XML;
		requestNo01_checkNumberOfPeople();
		requestNo02_CheckFirstPeopleExists();
		requestNo03_ChangeFirstPersonName();
		requestNo04_CreateNewPerson();
		requestNo05_RemoveCreatedPerson();
		requestNo06_getActivityTypes();
		requestNo07_getActivitiesForFirstAndLastPerson();
		requestNo08_GetPreviouslyDiscoveredActivity();
		requestNo09_putNewActivity();
		requestNo10_UpdateActivity();
		requestNo11_getActivitiesWithCertainData();

		System.out.println("SENDING JSON TESTS");
		System.out.println();
		MEDIA_TYPE = MediaType.APPLICATION_JSON;
		requestNo01_checkNumberOfPeople();
		requestNo02_CheckFirstPeopleExists();
		requestNo03_ChangeFirstPersonName();
		requestNo04_CreateNewPerson();
		requestNo05_RemoveCreatedPerson();
		requestNo06_getActivityTypes();
		requestNo07_getActivitiesForFirstAndLastPerson();
		requestNo08_GetPreviouslyDiscoveredActivity();
		requestNo09_putNewActivity();
		requestNo10_UpdateActivity();
		requestNo11_getActivitiesWithCertainData();
	}

	private static void requestNo01_checkNumberOfPeople() throws Exception {
		WebTarget webTarget = generateWebTarget("/person");
		
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
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
		
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
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
		
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response response = invocationBuilder.put(Entity.entity(first_person, MEDIA_TYPE));
		response.bufferEntity();
		
		first_person = response.readEntity(Person.class);
		
		printResponse(response, webTarget, 3, "PUT", MEDIA_TYPE, first_person.getFirstname().equals(previousName+"_EDIT"));
	}
	
	private static void requestNo04_CreateNewPerson() throws Exception {
		WebTarget webTarget = generateWebTarget("/person");
		
		ActivityType type = new ActivityType();
		type.setType("Dota");
		Activity activity = new Activity();
		activity.setDescription("Automatically Generated");
		activity.setName("Dota col Tonno - generated");
		activity.setPlace("A casa del Dezu - generated");
		activity.setStartdate(new Date(System.currentTimeMillis()));
		activity.setType(type);
		Person person = new Person();
		person.setActivitypreference(new ArrayList<Activity>());
		person.getActivitypreference().add(activity);
		person.setFirstname("Firstname");
		Random rand = new Random();
		person.setLastname("L+"+rand.nextInt(9999999));
		person.setBirthdate(new Date(System.currentTimeMillis()));
		
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response response = invocationBuilder.post(Entity.entity(person, MEDIA_TYPE));
		response.bufferEntity();
		
		inserted_person = response.readEntity(Person.class);
		
		printResponse(response, webTarget, 4, "POST", MEDIA_TYPE, response.getStatus()==201);
	}
	
	private static void requestNo05_RemoveCreatedPerson() throws Exception {
		WebTarget webTarget = generateWebTarget("/person/"+inserted_person.getId());
		
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response response = invocationBuilder.delete();
		response.bufferEntity();

		Invocation.Builder newInvocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response newResponse = newInvocationBuilder.get();
		newResponse.bufferEntity();
		
		printResponse(response, webTarget, 5, "DELETE","", newResponse.getStatus()==404);
	}
	
	private static void requestNo06_getActivityTypes() throws Exception {
		WebTarget webTarget = generateWebTarget("/activity_types");
		
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response response = invocationBuilder.get();
		response.bufferEntity();
		
		ActivityTypesWrapper types = response.readEntity(ActivityTypesWrapper.class);
		activity_types = new ArrayList<String>();
		activity_types.addAll(types.getActivityTypes());
		printResponse(response, webTarget, 6,"GET","", activity_types.size()>2 && activity_types.size()<4);
	}
	
	private static void requestNo07_getActivitiesForFirstAndLastPerson() throws Exception {
		Boolean found1 = false;
		for (String type : activity_types) {
			WebTarget webTarget = generateWebTarget("/person/"+first_person_id+"/"+type);
			Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
			Response response = invocationBuilder.get();
			if(response.getStatus() == 200) {
				found1 = true;
				break;
			}
		}
		Boolean found2 = false;
		Response response = null;
		WebTarget webTarget = null;
		for (String type : activity_types) {
			webTarget = generateWebTarget("/person/"+last_person_id+"/"+type);
			Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
			response = invocationBuilder.get();
			if(response.getStatus() == 200) {
				found2 = true;
				response.bufferEntity();
				ActivityWrapper activity = response.readEntity(ActivityWrapper.class);
				activity_id = activity.getActivity().get(0).getId();
				activity_type = activity.getActivity().get(0).getType().getType();
				break;
			}
		}
		
		printResponse(response, webTarget, 7,"GET","", found1 && found2);
	}
	
	private static void requestNo08_GetPreviouslyDiscoveredActivity() throws Exception {
		WebTarget webTarget = generateWebTarget("/person/"+last_person_id+"/"+activity_type+"/"+activity_id);
		
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response response = invocationBuilder.get();
		response.bufferEntity();
		
		printResponse(response, webTarget, 8, "GET","", response.getStatus()==200);
	}
	
	private static void requestNo09_putNewActivity() throws Exception {
		WebTarget webTarget = generateWebTarget("/person/"+first_person_id+"/"+activity_types.get(0));
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response response = invocationBuilder.get();
		int numberOfActivities = 0;
		if(response.getStatus() == 200) {
			response.bufferEntity();
			ActivityWrapper result = response.readEntity(ActivityWrapper.class);
			numberOfActivities=result.getActivity().size();
		}
		
		ActivityType type = new ActivityType();
		type.setType(activity_types.get(0));
		Activity activity = new Activity();
		activity.setDescription("Automatically generated");
		activity.setName("Randomly generated");
		activity.setPlace("Random place");
		activity.setStartdate(new Date(System.currentTimeMillis()));
		activity.setType(type);
		
		WebTarget newWebTarget = generateWebTarget("/person/"+first_person_id+"/"+activity_types.get(0));
		invocationBuilder =  newWebTarget.request(MEDIA_TYPE);
		Response newResponse = invocationBuilder.post(Entity.entity(activity, MEDIA_TYPE));
		newResponse.bufferEntity();
		
		webTarget = generateWebTarget("/person/"+first_person_id+"/"+activity_types.get(0));
		invocationBuilder =  webTarget.request(MEDIA_TYPE);
		response = invocationBuilder.get();
		ActivityWrapper result = response.readEntity(ActivityWrapper.class);
		int newNumberOfActivities=result.getActivity().size();
		created_activity = result.getActivity().get(result.getActivity().size()-1);
		
		printResponse(newResponse, newWebTarget, 9,"POST",MEDIA_TYPE, newNumberOfActivities==(numberOfActivities+1));
	}
	
	private static void requestNo10_UpdateActivity() throws Exception {
		created_activity.getType().setType(activity_types.get(1));
		WebTarget webTarget = generateWebTarget("/person/"+first_person_id+"/"+activity_types.get(1)+"/"+created_activity.getId());
		
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response response = invocationBuilder.put(Entity.entity(new ActivityType(), MEDIA_TYPE));
		response.bufferEntity();
		
		webTarget = generateWebTarget("/person/"+first_person_id+"/"+activity_types.get(1)+"/"+created_activity.getId());
		
		invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response newResponse = invocationBuilder.get();
		newResponse.bufferEntity();
		ActivityWrapper activities = newResponse.readEntity(ActivityWrapper.class);
		Activity activity = activities.getActivity().get(0);
		
		printResponse(newResponse, webTarget, 10, "PUT",MEDIA_TYPE, activity_types.get(1).equals(activity.getType().getType()));
	}
	
	private static void requestNo11_getActivitiesWithCertainData() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String after = df.format(created_activity.getStartdate());
		df = new SimpleDateFormat("dd");
		int day = Integer.parseInt( df.format(created_activity.getStartdate()) );
		df = new SimpleDateFormat("yyyy-MM");
		String before = df.format(created_activity.getStartdate())+"-"+String.format("%02d", day+1);
		
		WebTarget webTarget = generateWebTarget("/person/"+first_person_id+"/"+created_activity.getType().getType(), before, after);
		Invocation.Builder invocationBuilder =  webTarget.request(MEDIA_TYPE);
		Response response = invocationBuilder.get();
		response.bufferEntity();
		ActivityWrapper result = response.readEntity(ActivityWrapper.class);
		int numberOfActivities=result.getActivity().size();
		
		printResponse(response, webTarget, 11,"GET","", response.getStatus()==200 && numberOfActivities>=1);
	}
	
	private static WebTarget generateWebTarget(String target, String before, String after) {
		Client client = generateClient();
		WebTarget webTarget = client.target(ADDRESS).path(target).queryParam("before", before).queryParam("after", after);
		return webTarget;
	}
	
	private static WebTarget generateWebTarget(String target) {
		Client client = generateClient();
		WebTarget webTarget = client.target(ADDRESS).path(target);
		return webTarget;
	}

	private static Client generateClient() {
		Client client = ClientBuilder.newClient( new ClientConfig() ); //.register( LoggingFilter.class ) );
		return client;
	}
}
