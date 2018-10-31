package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
public class Category {

	@XmlAttribute(name = "id")
	public String id;
	
}
