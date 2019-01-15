package model;

import base.Regex;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "participation")
public class Participation {

    @XmlElement(name = "create-date")
    public String createDate;

    @XmlElement(name = "organization")
    public String organization;

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
        if(createDate != null && organization != null){
            sb.append("通过的");
        }
        if(notice != null){
            sb.append("《");
            sb.append(notice);
            sb.append("》");
        }
        sb.append("加入");
        return sb.toString();
    }

    public void valid() {
        if(!Regex.isValidName(notice)){
            throw new ArchieveException("文件不符合规定：%s", notice);
        }
    }
}
