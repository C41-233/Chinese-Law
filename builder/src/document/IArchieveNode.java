package document;

import java.util.ArrayList;
import java.util.List;

public interface IArchieveNode {

	public ArchiveNode getParent();
	
	public String getName();
	
	public default String getPath() {
		ArchiveNode parent = getParent();
		String name = getName();
		
		if(parent != null) {
			return parent.getPath() + "/" + name;
		}
		return name;
	}
	
	public default String getFilename() {
		return getPath().replace('/', '-');
	}

	public default List<IArchieveNode> getNeighbours(){
		ArchiveNode parent = getParent();
		List<IArchieveNode> list = new ArrayList<>();
		if(parent != null) {
			list.addAll(parent.collections);
			list.addAll(parent.nodes);
		}
		return list;
	}
	
	public default boolean isRoot() {
		return getParent() == null;
	}

}
