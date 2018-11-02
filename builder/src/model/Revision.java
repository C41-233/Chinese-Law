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
		if(id != null || executeDate != null) {
			sb.append("��");
			sb.append(id);
			sb.append("����");
			sb.append(executeDate);
			sb.append("��ʩ��");
			sb.append("��");
		}
		return sb.toString();
	}
	
}
