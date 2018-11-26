package page;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import document.ArchieveCollection;
import document.ArchieveNode;
import document.ArchieveRoot;
import document.Law;

public class PageNational implements IPage{

	private HashSet<Law> laws = new HashSet<>();
	private List<ArchieveRoot> roots = new ArrayList<>();
	private List<ArchieveCollection> collections = new ArrayList<>();
	private List<ArchieveNode> nodes = new ArrayList<>();
	
	public PageNational(List<ArchieveRoot> roots) {
		for(ArchieveRoot src : roots) {
			ArchieveRoot dest = createRoot(src);
			if(dest != null) {
				this.roots.add(dest);
			}
		}
		for(ArchieveRoot root : this.roots) {
			root.setRoots(this.roots);
		}
	}

	private ArchieveRoot createRoot(ArchieveRoot src) {
		ArchieveRoot dest = new ArchieveRoot(src.getName());
		return (ArchieveRoot) DoCreateNode(dest, src);
	}
	
	private ArchieveNode createNode(ArchieveNode parent, ArchieveNode src) {
		ArchieveNode dest = new ArchieveNode(parent, src.getName());
		return DoCreateNode(dest, src);
	}
	
	private ArchieveNode DoCreateNode(ArchieveNode dest, ArchieveNode src) {
		for(ArchieveNode node : src.nodes) {
			ArchieveNode destNode = createNode(dest, node);
			if(destNode != null) {
				dest.nodes.add(destNode);
			}
		}

		for(ArchieveCollection collection : src.collections) {
			ArchieveCollection destCollection = createCollection(dest, collection);
			if(destCollection != null) {
				dest.collections.add(destCollection);
			}
		}
		
		if(dest.nodes.size() > 0 || dest.collections.size() > 0) {
			nodes.add(dest);
			return dest;
		}
		return null;
	}

	private ArchieveCollection createCollection(ArchieveNode parent, ArchieveCollection src) {
		ArchieveCollection dest = new ArchieveCollection(parent, src.getName());

		for(Law law : src.laws) {
			if(law.isNational()) {
				this.laws.add(law);
				dest.laws.add(law);
			}
		}
		
		if(dest.laws.size() > 0) {
			collections.add(dest);
			return dest;
		}
		return null;
	}
	
	@Override
	public String getBaseDirectoryName() {
		return "collections";
	}

	@Override
	public List<ArchieveCollection> getCollections() {
		return collections;
	}

	@Override
	public List<ArchieveNode> getNodes() {
		return nodes;
	}

	public List<ArchieveRoot> getRoots() {
		return roots;
	}

	@Override
	public String getTemplateCollectionName() {
		return "collection.template.html";
	}

	@Override
	public String getTemplateNodeName() {
		return "node.template.html";
	}

}
