package document;

import model.Deprecated;
import model.Document;
import model.History;

import java.util.List;

public class Law {

	public final String name;
	
	private Document document;
	
	private final ArchiveCollection parent;
	
	public Law(ArchiveCollection parent, String name) {
		this.parent = parent;
		this.name = name;
	}

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
			if(history.createDate != null){
				DocumentDate date = new DocumentDate(history.createDate);
				year = Math.max(year, date.year);
			}
		}
		return year;
	}
	
	public List<History> getHistories(){
		if(document == null) {
			return null;
		}
		return document.histories;
	}

	public boolean isDeprecated() {
		if(document == null) {
			return false;
		}
		return document.deprecated != null;
	}
	
	public String getDeprecatedReplaceDocument() {
		if(document == null || document.deprecated == null) {
			return null;
		}
		return document.deprecated.document;
	}

	public Deprecated getDeprecatedInfo(){
		if(document == null){
			return null;
		}
		return document.deprecated;
	}

	public ArchiveCollection getParent() {
		return parent;
	}

	//是否全国性法律
	public boolean isNational() {
		return document == null || document.category.isNational();
	}

	//地方性法律
	public boolean isLocal() {
		return document != null && document.category.isLocal();
	}
	
	public List<String> getCategoryPath(){
		return document == null ? null : document.category.getPath();
	}
	
}
