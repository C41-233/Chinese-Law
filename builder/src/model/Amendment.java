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
		sb.append("����");
		sb.append(createDate);
		sb.append(organization);
		sb.append("ͨ���ġ�");
		sb.append(notice);
		sb.append("������");
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
