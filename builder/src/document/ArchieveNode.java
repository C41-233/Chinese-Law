package document;

import java.util.ArrayList;
import java.util.List;

public class ArchieveNode {

	public String name;
	
	public List<ArchieveNode> childs = new ArrayList<>();
	public List<ArchieveCollection> documents = new ArrayList<>();
	
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
	
}
