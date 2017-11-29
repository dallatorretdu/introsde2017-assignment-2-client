package unitn.dallatorre.client;

import java.io.StringReader;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ClientEvaluation {

	public static void printResponse(Response response, WebTarget webTarget, int number, String method, String contentType, Boolean result) {
		String resString = ((result) ? "OK" : "ERROR");
		System.out.println("Request #["+number+"]: ["+method+"] ["+webTarget.getUri()+"] Accept: ["+response.getMediaType()+"] Content-type: ["+contentType+"] \n" + 
							"\t=> Result: ["+resString+"]\n" + 
							"\t=> HTTP Status: ["+response.getStatus()+"]\n" + 
							new XmlFormatter().format(response.readEntity(String.class))
							);
	}
	
	protected static Document loadXMLFromString(String xml) throws Exception{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}

}