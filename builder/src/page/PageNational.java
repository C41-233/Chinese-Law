package page;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import document.ArchiveCollection;
import document.ArchiveNode;
import document.ArchiveRoot;
import document.Law;

public class PageNational implements IPage{

	private HashSet<Law> laws = new HashSet<>();
	private List<ArchiveRoot> roots = new ArrayList<>();
	private List<ArchiveCollection> collections = new ArrayList<>();
	private List<ArchiveNode> nodes = new ArrayList<>();
	
	public PageNational(List<ArchiveRoot> roots) {
		for(ArchiveRoot src : roots) {
			ArchiveRoot dest = createRoot(src);
			if(dest != null) {
				this.roots.add(dest);
			}
		}
		for(ArchiveRoot root : this.roots) {
			root.setRoots(this.roots);
		}
	}

	private ArchiveRoot createRoot(ArchiveRoot src) {
		ArchiveRoot dest = new ArchiveRoot(src.getName());
		return (ArchiveRoot) DoCreateNode(dest, src);
	}
	
	private ArchiveNode createNode(ArchiveNode parent, ArchiveNode src) {
		ArchiveNode dest = new ArchiveNode(parent, src.getName());
		return DoCreateNode(dest, src);
	}
	
	private ArchiveNode DoCreateNode(ArchiveNode dest, ArchiveNode src) {
		for(ArchiveNode node : src.nodes) {
			ArchiveNode destNode = createNode(dest, node);
			if(destNode != null) {
				dest.nodes.add(destNode);
			}
		}

		for(ArchiveCollection collection : src.collections) {
			ArchiveCollection destCollection = createCollection(dest, collection);
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

	private ArchiveCollection createCollection(ArchiveNode parent, ArchiveCollection src) {
		ArchiveCollection dest = new ArchiveCollection(parent, src.getName());

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
	public List<ArchiveCollection> getCollections() {
		return collections;
	}

	@Override
	public List<ArchiveNode> getNodes() {
		return nodes;
	}

	public List<ArchiveRoot> getRoots() {
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
