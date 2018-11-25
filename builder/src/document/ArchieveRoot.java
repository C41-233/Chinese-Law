package document;

import java.util.List;

public class ArchieveRoot extends ArchieveNode{

	public ArchieveRoot(String name) {
		super(null, name);
	}

	private List<ArchieveRoot> roots;
	
	public void setRoots(List<ArchieveRoot> rootNodes) {
		this.roots = rootNodes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IArchieveNode> getNeighbours() {
		return (List<IArchieveNode>)(Object)roots;
	}
	
}
