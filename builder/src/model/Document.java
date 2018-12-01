package model;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "document")
public class Document {

	@XmlElement(name = "category")
	public Category category;
	
	@XmlElementWrapper(name = "histories")
	@XmlAnyElement(lax = true)
	public List<History> histories;

	@XmlElement(name = "deprecated")
	public Deprecated deprecated;
	
	public void valid() {
		category.valid();
		for (History history : histories) {
			history.valid();
		}
		if(deprecated != null){
			deprecated.valid();
		}
	}
	
}
