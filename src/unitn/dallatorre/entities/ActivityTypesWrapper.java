package unitn.dallatorre.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
//Refer to the server's entities
@XmlRootElement(name="activityTypes")
@XmlAccessorType(XmlAccessType.FIELD)
public class ActivityTypesWrapper implements Serializable{
	
	private List<String> activityType;
	
	public List<String> getActivityTypes() {
		return activityType;
	}
	
	public void setActivityTypes(List<String> types) {
		activityType = types;
	}
}
