package document;

import java.util.ArrayList;
import java.util.List;

public class ArchieveNode implements IArchieveNode{

	private final ArchieveNode parent;
	private final String name;
	
	public final List<ArchieveNode> collections = new ArrayList<>();
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
			sb.append("\n" + padding + "\t-" + document.getName());
		}
		for(ArchieveNode child : collections) {
			sb.append("\n");
			child.toString(sb, padding + "\t");
		}
	}

	public List<IArchieveNode> getChildren(){
		List<IArchieveNode> nodes = new ArrayList<>();
		nodes.addAll(collections);
		nodes.addAll(documents);
		return nodes;
	}
	
	@Override
	public ArchieveNode getParent() {
		return parent;
	}

	@Override
	public String getName() {
		return name;
	}

}
