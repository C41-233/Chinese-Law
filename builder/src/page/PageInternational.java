package page;

import document.*;

import java.util.ArrayList;
import java.util.List;

public class PageInternational implements IPage{

	private List<ArchiveCollection> collections = new ArrayList<>();
	private List<ArchiveNode> nodes = new ArrayList<>();

	private List<ArchiveRoot> roots = new ArrayList<>();

	public PageInternational(List<Law> laws) {
		for(Law law : laws) {
			if(law.isInternational()) {
				createCollection(law);
			}
		}
		
		for(ArchiveRoot root : roots) {
			root.setRoots(roots);
		}
	}

	private void createCollection(Law law) {
		List<String> paths = new ArrayList<>();
		IArchieveNode an = law.getParent();
		paths.add(an.getName());
		while(!an.isRoot()){
			an = an.getParent();
			paths.add(0, an.getName());
		}
		ArchiveRoot root = getOrCreateRoot(paths.get(0));
		fillNodes(root, law);
	}
	
	private void fillNodes(ArchiveNode root, Law law) {
		String name = law.getParent().getName();
		List<String> nodes = new ArrayList<>();
		IArchieveNode node = law.getParent().getParent();
		while(node != null) {
			nodes.add(node.getName());
			node = node.getParent();
		}

		ArchiveNode theNode = root;
		for(int i = nodes.size()-2; i>=0; i--) {
			String path = nodes.get(i);
			theNode = getOrCreateNode(theNode, path);
		}

		ArchiveCollection collection = getOrCreateCollection(theNode, name);
		collection.laws.add(law);
	}

	private ArchiveRoot getOrCreateRoot(String name) {
		for(ArchiveRoot root : roots) {
			if(root.getName().equals(name)) {
				return root;
			}
		}
		ArchiveRoot root = new ArchiveRoot(name);
		roots.add(root);
		nodes.add(root);
		return root;
	}

	private ArchiveNode getOrCreateNode(ArchiveNode parent, String name) {
		for(ArchiveNode child : parent.nodes) {
			if(child.getName().equals(name)) {
				return child;
			}
		}
		ArchiveNode child = new ArchiveNode(parent, name);
		parent.nodes.add(child);
		nodes.add(child);
		return child;
	}

	private ArchiveCollection getOrCreateCollection(ArchiveNode parent, String name) {
		for(ArchiveCollection child : parent.collections) {
			if(child.getName().equals(name)) {
				return child;
			}
		}
		ArchiveCollection child = new ArchiveCollection(parent, name);
		parent.collections.add(child);
		collections.add(child);
		return child;
	}

	@Override
	public String getBaseDirectoryName() {
		return "internationals";
	}

	@Override
	public String getTemplateCollectionName() {
		return "collection.template.html";
	}

	@Override
	public String getTemplateNodeName() {
		return "node.template.html";
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

}
