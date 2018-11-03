package model;

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
		if(notice != null) {
			sb.append("ͨ���ġ�");
			sb.append(notice);
			sb.append("��");
		}
		sb.append("����");
		if(id != null || executeDate != null) {
			sb.append("��");
			if(id != null) {
				sb.append(id);
			}
			if(id != null && executeDate != null) {
				sb.append("��");
			}
			if(executeDate != null) {
				sb.append("��");
				sb.append(executeDate);
				sb.append("��ʩ��");
			}
			sb.append("��");
		}
		return sb.toString();
	}
	
	@Override
	public void valid() {
		super.valid();
		if(notice != null) {
			if(notice.contains("<") || notice.contains(">")) {
				throw new ArchieveException("notice�а�������ķ���");
			}
		}
	}
	
}
