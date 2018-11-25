package document;

import java.util.ArrayList;
import java.util.List;

public class ArchieveCollection{

	public final ArchieveNode parent;
	public final String name;
	
	public final List<Law> laws = new ArrayList<>();
	
	public ArchieveCollection(ArchieveNode parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	public String getPath() {
		return parent.getPath() + "/" + name;
	}
	
	public List<ArchieveCollection> getNeighbours(){
		return parent.documents;
	}
	
}
