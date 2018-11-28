package document;

import java.util.ArrayList;
import java.util.List;

public class ArchiveNode implements IArchieveNode{

	private final ArchiveNode parent;
	private final String name;
	
	public final List<ArchiveNode> nodes = new ArrayList<>();
	public final List<ArchiveCollection> collections = new ArrayList<>();

	public ArchiveNode(ArchiveNode parent, String name) {
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
		for(ArchiveCollection document : collections) {
			sb.append("\n" + padding + "\t-" + document.getName());
		}
		for(ArchiveNode child : nodes) {
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
	public ArchiveNode getParent() {
		return parent;
	}

	@Override
	public String getName() {
		return name;
	}

}
