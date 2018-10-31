package model;

import java.sql.SQLTransactionRollbackException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "amendment")
public class Amendment extends History{

	@XmlElement(name = "notice")
	public String notice;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("根据");
		sb.append(createDate);
		sb.append(organization);
		sb.append("通过的《");
		sb.append(notice);
		sb.append("》修正");
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
