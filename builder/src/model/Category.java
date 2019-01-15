package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@XmlRootElement(name = "category")
public class Category {

	public static final String CategoryRegex = "宪法|法律|国家规范性文件|行政法规|部门规章|司法解释|党章|地方(司法文件)|国际公约";

	@XmlAttribute(name = "id")
	public String id;

	@XmlValue
	public String path;
	
	public void valid() {
		if(!id.matches(CategoryRegex)) {
			throw new ArchieveException("文件类型不符合：%s", id);
		}
	}

	public boolean isNational() {
		return !id.startsWith("地方");
	}

	public boolean isLocal() {
		return id.startsWith("地方");
	}

	public boolean isInternational() {
		return id.startsWith("国际");
	}

	public List<String> getPath() {
		List<String> paths = new ArrayList<>();
		Collections.addAll(paths, path.split("\\w"));
		return paths;
	}

}
