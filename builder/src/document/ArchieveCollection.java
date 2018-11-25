package document;

import java.util.ArrayList;
import java.util.List;

public class ArchieveCollection implements IArchieveNode{

	private final ArchieveNode parent;
	private final String name;
	
	public final List<Law> laws = new ArrayList<>();
	
	public ArchieveCollection(ArchieveNode parent, String name) {
		this.parent = parent;
		this.name = name;
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
