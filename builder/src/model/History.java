package model;

import javax.xml.bind.annotation.XmlElement;

public abstract class History {

	@XmlElement(name = "create-date")
	public String createDate;

	@XmlElement(name = "organization")
	public String organization;

	@XmlElement(name = "execute-date")
	public String executeDate;

	@XmlElement(name = "id")
	public String id;

	public void valid() {
	}
}
