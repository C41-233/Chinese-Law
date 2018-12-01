package model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "deprecated")
public class Deprecated {

	public String date;
	public String document;
	public String notice;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(date);
		sb.append("根据");
		if(document != null){
			sb.append('《').append(document).append("》");
		}
		if(notice != null){
			sb.append('《').append(notice).append("》");
		}
		sb.append("废止");
		return sb.toString();
	}
}
