package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "amendment")
public class Amendment extends History{

	@XmlElement(name = "notice")
	public String notice;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("根据");
		if(createDate != null){
			sb.append(createDate);
		}
		if(organization != null){
			sb.append(organization);
		}
		if(notice != null) {
			sb.append("通过的《");
			sb.append(notice);
			sb.append("》");
		}
		sb.append("修正");
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
				list.add("自" + executeDate + "起施行");
			}
			sb.append(String.join("，", list));
			sb.append("）");
		}
		return sb.toString();
	}
	
	@Override
	public void valid() {
		super.valid();
		if(notice != null) {
			if(notice.contains("<") || notice.contains(">")) {
				throw new ArchieveException("notice中包含错误的符号");
			}
		}
	}
	
}
