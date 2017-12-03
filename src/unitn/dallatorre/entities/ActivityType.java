package unitn.dallatorre.entities;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
//Refer to the server's entities
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="activityType")
public class ActivityType implements Serializable {
    @XmlElement(name = "id", required = true)
    private String type;

	public ActivityType() {
		
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.getType().equals(((ActivityType) obj).getType());
	}
}