package model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "revision")
public class Revision extends History{

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(createDate != null){
			sb.append(createDate);
		}
		if(organization != null){
			sb.append(organization);
		}
		if(createDate != null && organization != null){
			sb.append("通过");
		}
		if(id != null || noticeDate != null || executeDate != null) {
			sb.append("（");
			List<String> list = new ArrayList<>();
			if(id != null){
				list.add(id);
			}
			if(noticeDate != null){
				list.add(noticeDate + "公布");
			}
			if(executeDate != null){
				if(root.isInternational()){
					list.add(executeDate + "生效");
				}
				else{
					list.add("自" + executeDate + "起施行");
				}
			}
			sb.append(String.join("，", list));
			sb.append("）");
		}
		return sb.toString();
	}
	
}
