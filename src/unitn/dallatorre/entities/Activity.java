package unitn.dallatorre.entities;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Refer to the server's entities
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Activity implements Serializable{
    @XmlAttribute(name = "id", required = true)
    protected Integer id;
	
	@XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String place;
    
    @XmlElement(required = true)
    protected ActivityType type;
    
    @XmlElement(required = true)
    protected Date startdate;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public ActivityType getType() {
		return type;
	}
	public void setType(ActivityType type) {
		this.type = type;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getId().equals(((Activity) obj).getId())) && (this.getName().equals(((Activity) obj).getName()));
	}
	public boolean isInBetween(Date beginDate, Date endDate) {
		return (startdate.before(endDate) && startdate.after(beginDate));
	}

}