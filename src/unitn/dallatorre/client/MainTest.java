package unitn.dallatorre.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;

public class MainTest {
	private static final String ADDRESS = "http://localhost:8080/SDE_2_SERVER";

	public static void main(String[] argv) {
		System.out.println("SERVER URL : " + ADDRESS);
		try {

			Client client = Client.create();
			WebResource webResource = client.resource(ADDRESS+"/person");

			ClientResponse response = webResource.accept("application/xml").get(ClientResponse.class);

			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			
			printResponse(response, webResource, 0,"GET","");

			String output = response.getEntity(String.class);

		  } catch (Exception e) {

			e.printStackTrace();

		  }
	}
	
	public static void printResponse(ClientResponse response, WebResource webResource, int number, String method, String contentType) {
		response.bufferEntity();
		System.out.println("Request #["+number+"]: ["+method+"] ["+webResource.getURI()+"] Accept: ["+response.getType()+"] Content-type: ["+contentType+"] \n" + 
							"\t=> Result: ["+response.getStatusInfo()+"]\n" + 
							"\t=> HTTP Status: ["+response.getStatus()+"]\n" + 
							"[BODY]\n" + 
							"\t"+response.getEntity(String.class));
	}
}
