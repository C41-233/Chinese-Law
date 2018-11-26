package document;

import java.util.ArrayList;
import java.util.List;

public class ArchieveNode implements IArchieveNode{

	private final ArchieveNode parent;
	private final String name;
	
	public final List<ArchieveNode> nodes = new ArrayList<>();
	public final List<ArchieveCollection> collections = new ArrayList<>();

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
		for(ArchieveCollection document : collections) {
			sb.append("\n" + padding + "\t-" + document.getName());
		}
		for(ArchieveNode child : nodes) {
			sb.append("\n");
			child.toString(sb, padding + "\t");
		}
	}

	public List<IArchieveNode> getChildren(){
		List<IArchieveNode> nodes = new ArrayList<>();
		nodes.addAll(this.nodes);
		nodes.addAll(this.collections);
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
