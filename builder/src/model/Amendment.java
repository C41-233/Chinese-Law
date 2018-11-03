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
		sb.append("根据");
		sb.append(createDate);
		sb.append(organization);
		if(notice != null) {
			sb.append("通过的《");
			sb.append(notice);
			sb.append("》");
		}
		sb.append("修正");
		if(id != null || executeDate != null) {
			sb.append("（");
			if(id != null) {
				sb.append(id);
			}
			if(id != null && executeDate != null) {
				sb.append("，");
			}
			if(executeDate != null) {
				sb.append("自");
				sb.append(executeDate);
				sb.append("起施行");
			}
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
