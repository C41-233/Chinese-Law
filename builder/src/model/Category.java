package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
public class Category {

	public static final String CategoryRegex = "�ܷ�|����|��������|˾������|����|�ط�(˾���ļ�)";

	@XmlAttribute(name = "id")
	public String id;

	public void valid() {
		if(!id.matches(CategoryRegex)) {
			throw new ArchieveException("�ļ����Ͳ����ϣ�%s", id);
		}
	}

	public boolean isNational() {
		return !id.startsWith("�ط�");
	}
	
}
