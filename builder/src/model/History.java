package model;

import base.Regex;
import document.DocumentDate;

import javax.xml.bind.annotation.XmlElement;

public abstract class History {

	public static final String[] RegexOrganization = new String[]{
			String.format("第(%s)届全国人民代表大会第(%s)次会议", Regex.ChineseNumber, Regex.ChineseNumber),
			String.format("第(%s)届全国人民代表大会常务委员会第(%s)次会议", Regex.ChineseNumber, Regex.ChineseNumber),
			String.format("中国共产党第(%s)次全国代表大会", Regex.ChineseNumber),
			String.format("最高人民法院审判委员会第(%s)次会议", Regex.Number),
			String.format("国务院第(%s)次常务会议", Regex.Number),
			"(.+)省高级人民法院",
	};

	private static final String RegexDepartment = "国务院|财政部|国家税务总局";

	public static final String[] RegexId = new String[]{
			String.format("(%s)届主席令第(%s)号", Regex.ChineseNumber, Regex.ChineseNumber),
			String.format("第(%s)届全国人民代表大会第(%s)次会议主席团公告第(%s)号", Regex.ChineseNumber, Regex.ChineseNumber, Regex.ChineseNumber),
			String.format("(%s)届主席令第(%s)号", Regex.ChineseNumber, Regex.ChineseNumber),
			String.format("(%s)(、(%s))*令第(%s)号", RegexDepartment, RegexDepartment, Regex.Number),
			String.format("法释〔[0-9]{4}〕(%s)号", Regex.Number),
	};

	@XmlElement(name = "create-date")
	public String createDate;

	@XmlElement(name = "notice-date")
	public String noticeDate;

	@XmlElement(name = "organization")
	public String organization;

	@XmlElement(name = "execute-date")
	public String executeDate;

	@XmlElement(name = "id")
	public String id;

	public void valid() {
		if(createDate != null){
			DocumentDate.valid(createDate);
		}
		if(executeDate != null){
			DocumentDate.valid(executeDate);
		}
		if(noticeDate != null){
			DocumentDate.valid(noticeDate);
		}
		if(organization != null){
			if(!Regex.isMatch(organization, RegexOrganization)){
				throw new ArchieveException("organization不符合：%s", organization);
			}
		}
		if(id != null){
			if(!Regex.isMatch(id, RegexId)){
				throw new ArchieveException("id不符合：%s", id);
			}
		}
	}
}
