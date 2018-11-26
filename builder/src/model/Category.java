package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
public class Category {

	public static final String CategoryRegex = "宪法|法律|行政法规|司法解释|党章|地方(司法文件)";

	@XmlAttribute(name = "id")
	public String id;

	public void valid() {
		if(!id.matches(CategoryRegex)) {
			throw new ArchieveException("文件类型不符合：%s", id);
		}
	}

	public boolean isNational() {
		return !id.startsWith("地方");
	}
	
}
