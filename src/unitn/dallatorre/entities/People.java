package unitn.dallatorre.entities;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
//Refer to the server's entities
@XmlRootElement(name="People")
@XmlAccessorType(XmlAccessType.FIELD)
public class People implements Serializable{
	
	protected List<Person> person;
	
	public List<Person> getPersons() {
		return person;
	}
	
	public void setPersons(List<Person> personList) {
		person = personList;
	}
}