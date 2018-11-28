package document;

import java.util.List;

public class ArchiveRoot extends ArchiveNode {

	public ArchiveRoot(String name) {
		super(null, name);
	}

	private List<ArchiveRoot> roots;
	
	public void setRoots(List<ArchiveRoot> rootNodes) {
		this.roots = rootNodes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IArchieveNode> getNeighbours() {
		return (List<IArchieveNode>)(Object)roots;
	}
	
}
