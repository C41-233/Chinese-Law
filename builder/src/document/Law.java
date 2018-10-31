package document;

import java.util.List;

import model.Document;
import model.History;

public class Law {

	public String name;
	
	private Document document;
	
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public String getCategory() {
		if(document == null || document.category == null) {
			return null;
		}
		return document.category.id;
	}
	
	public int getVersion() {
		int year = 0;
		if(document == null || document.histories == null) {
			return 0;
		}
		for(History history : document.histories) {
			DocumentDate date = new DocumentDate(history.createDate);
			year = Math.max(year, date.year);
		}
		return year;
	}
	
	public List<History> getHistories(){
		if(document == null) {
			return null;
		}
		return document.histories;
	}
	
}
