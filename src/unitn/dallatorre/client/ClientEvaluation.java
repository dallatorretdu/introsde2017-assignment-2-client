package unitn.dallatorre.client;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClientEvaluation {
	// Method used to print the required debug message and extract the most out of the objects
	//response is the response, which should be already buffered. WebTarget is the target used to launch the request
	//RequestSequenceNumber only indicates the request # by exercise requirements
	//restMethod indicates weather the request was PUT, POST, GET, ... I was unable to extract this from the response and the target
	//content type indicates the type of the payload sent
	//result is the boolean value that indicates the failure or the success of the test
	public static void printResponse(Response response, WebTarget webTarget, int requestSequenceNumber, String restMethod, String contentType, Boolean result) {
		String resString = ((result) ? "OK" : "ERROR");
		System.out.println("Request #["+requestSequenceNumber+"]: ["+restMethod+"] ["+webTarget.getUri()+"] Accept: ["+response.getMediaType()+"] Content-type: ["+contentType+"] \n" + 
							"\t=> Result: ["+resString+"]\n" + 
							"\t=> HTTP Status: ["+response.getStatus()+"]");
		if ( response.getStatus()!=204 ) {
			if( response.getMediaType().equals(MediaType.APPLICATION_XML) ) {
				System.out.println(new XmlFormatter().format(response.readEntity(String.class)));
				//If the payload is XML, pretty print it.
			}
			else {
				System.out.println(response.readEntity(String.class));
			}
		}
	}

}