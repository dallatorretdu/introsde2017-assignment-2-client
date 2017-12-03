package unitn.dallatorre.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
//Refer to the server's entities
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable{
    @XmlAttribute(name = "id", required = true)
    private Integer id;
    
	@XmlElement(required = true)
    private String firstname;
    @XmlElement(required = true)
    private String lastname;
    @XmlElement(required = true)
    private Date birthdate;   
    @XmlElement(required = false)
    private List<Activity> activitypreference;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public List<Activity> getActivitypreference() {
		return activitypreference;
	}

	public void setActivitypreference(List<Activity> activitypreference) {
		this.activitypreference = activitypreference;
	}
	
	public String toString() {
		String out = this.getFirstname() + " " + this.getLastname() + "<br>   Activities: ";
		for (Activity activity: this.getActivitypreference()) {
		    out += "<br>       " + activity.getName() + " at " + activity.getPlace() +" of type: " + activity.getType().getType();
		}
		return out;
	}
}