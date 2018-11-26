package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "category")
public class Category {

	public static final String CategoryRegex = "�ܷ�|����|��������|˾������|����|�ط�(˾���ļ�)";

	@XmlAttribute(name = "id")
	public String id;

	@XmlValue
	public String path;
	
	public void valid() {
		if(!id.matches(CategoryRegex)) {
			throw new ArchieveException("�ļ����Ͳ����ϣ�%s", id);
		}
	}

	public boolean isNational() {
		return !id.startsWith("�ط�");
	}

	public boolean isLocal() {
		return id.startsWith("�ط�");
	}

	public List<String> getPath() {
		List<String> paths = new ArrayList<>();
		for(String token : path.split("\\w")) {
			paths.add(token);
		}
		return paths;
	}
	
}
