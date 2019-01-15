package model;

import base.Regex;

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

	@XmlElement(name = "short-name")
	public String shortName;

	@XmlElement(name = "en-name")
	public String englishName;

	@XmlElement(name = "participation")
	public Participation participation;

	public boolean isInternational(){
		if(category == null){
			return false;
		}
		return category.isInternational();
	}

	public void valid() {
		category.valid();
		if(histories != null){
			for (History history : histories) {
				history.root = this;
				history.valid();
			}
		}
		if(deprecated != null){
			deprecated.valid();
		}

		if(shortName != null && !Regex.isValidName(shortName)){
			throw new ArchieveException("简称包含非法字符：%s", shortName);
		}

		if(participation != null){
			participation.valid();
		}
	}
	
}
