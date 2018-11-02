package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "revision")
public class Revision extends History{

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(createDate);
		sb.append(organization);
		sb.append("通过");
		if(id != null || executeDate != null) {
			sb.append("（");
			sb.append(id);
			sb.append("，自");
			sb.append(executeDate);
			sb.append("起施行");
			sb.append("）");
		}
		return sb.toString();
	}
	
}
