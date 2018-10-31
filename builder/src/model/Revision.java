package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "revision")
public class Revision extends History{

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(createDate);
		sb.append(organization);
		sb.append("ͨ��");
		return sb.toString();
	}
	
}
