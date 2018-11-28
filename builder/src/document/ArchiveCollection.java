package document;

import java.util.ArrayList;
import java.util.List;

public class ArchiveCollection implements IArchieveNode{

	private final ArchiveNode parent;
	private final String name;
	
	public final List<Law> laws = new ArrayList<>();
	
	public ArchiveCollection(ArchiveNode parent, String name) {
		this.parent = parent;
		this.name = name;
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
