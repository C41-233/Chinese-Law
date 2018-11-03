package document;

import java.util.ArrayList;
import java.util.List;

public class ArchieveNode {

	public final ArchieveNode parent;
	public final String name;
	
	public final List<ArchieveNode> childs = new ArrayList<>();
	public final List<ArchieveCollection> documents = new ArrayList<>();

	public ArchieveNode(ArchieveNode parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(sb, "");
		return sb.toString();
	}
	
	public void toString(StringBuilder sb, String padding) {
		sb.append(padding + name);
		for(ArchieveCollection document : documents) {
			sb.append("\n" + padding + "\t-" + document.name);
		}
		for(ArchieveNode child : childs) {
			sb.append("\n");
			child.toString(sb, padding + "\t");
		}
	}

	public String getPath() {
		if(parent != null) {
			return parent + "/" + name;
		}
		return name;
	}
	
}
