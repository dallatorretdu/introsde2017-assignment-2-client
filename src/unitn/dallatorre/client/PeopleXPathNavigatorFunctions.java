package unitn.dallatorre.client;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class PeopleXPathNavigatorFunctions {
	
	//Returns a generic NodeList result for all paths starting from /people does not handle exceptions
	private NodeList getPeopleCustomXpathResource(Document document, String path) throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr;
		expr = xpath.compile("/People"+path);
		Object result;
		result = expr.evaluate(document, XPathConstants.NODESET);
		return (NodeList) result;
	}

	//Executes the above method but handles the exceptions making so it returns a null NodeList in case of failure or empty file
	private NodeList executeGenericPeopleXPath(Document document, String path) {
		NodeList nodes = null;
		try {
			nodes = getPeopleCustomXpathResource(document, path);
		} catch (XPathExpressionException e) {
			return null;
		}
		if (nodes.getLength()==0) {
			return null;
		}
		return nodes;
	}
	
	// FUNCTIONS TO GET PEOPLE ID's
	public Integer getPeopleFirstIdInList(Document document) {
		String path = "/person/@id";
		NodeList nodes = executeGenericPeopleXPath(document, path);
		if(nodes == null)
			return null;
		return Integer.parseInt( nodes.item(0).getTextContent() );
	}
	
	public Integer getPeopleLastIdInList(Document document) {
		String path = "/person/@id";
		NodeList nodes = executeGenericPeopleXPath(document, path);
		if(nodes == null)
			return null;
		return Integer.parseInt( nodes.item(nodes.getLength()-1).getTextContent() );
	}
	
	public Integer getPeopleCountInList(Document document) {
		String path = "/person/@id";
		NodeList nodes = executeGenericPeopleXPath(document, path);
		if(nodes == null)
			return 0;
		return nodes.getLength();
	}

}